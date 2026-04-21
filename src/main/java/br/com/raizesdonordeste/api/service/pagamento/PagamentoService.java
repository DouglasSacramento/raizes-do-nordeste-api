package br.com.raizesdonordeste.api.service.pagamento;

import br.com.raizesdonordeste.api.domain.pagamento.Pagamento;
import br.com.raizesdonordeste.api.domain.pagamento.enums.StatusPagamento;
import br.com.raizesdonordeste.api.domain.pedido.ItemPedido;
import br.com.raizesdonordeste.api.domain.pedido.Pedido;
import br.com.raizesdonordeste.api.domain.pedido.enums.StatusPedido;
import br.com.raizesdonordeste.api.domain.unidade.ItemEstoque;
import br.com.raizesdonordeste.api.infrastructure.exception.exceptions.EstoqueInsuficienteException;
import br.com.raizesdonordeste.api.infrastructure.exception.exceptions.PagamentoRecusadoException;
import br.com.raizesdonordeste.api.infrastructure.exception.exceptions.PagamentoStatusInvalidoException;
import br.com.raizesdonordeste.api.repository.ItemEstoqueRepository;
import br.com.raizesdonordeste.api.repository.PagamentoRepository;
import br.com.raizesdonordeste.api.repository.PedidoRepository;
import br.com.raizesdonordeste.api.service.estoque.dto.ErroEstoqueDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final ItemEstoqueRepository itemEstoqueRepository;
    private final CalculadoraFidelidadeService calculadoraFidelidadeService;

    @Transactional
    public Pagamento processarPagamento(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido informado não existe."));

        List<StatusPedido> statusPermitidos = List.of(StatusPedido.AGUARDANDO_PAGAMENTO);
        if (!statusPermitidos.contains(pedido.getStatusPedido())) {
            throw new PagamentoStatusInvalidoException("Pedido não está com status válido para pagamento.");
        }

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setValor(pedido.getValorTotal());
        pagamento.setCodigoGateway(UUID.randomUUID().toString());
        pagamento.setMetodoPagamento(pedido.getMetodoPagamento());
        pagamento.setPedido(pedido);

        boolean resultadoPagamento = Math.random() > 0.5;

        if (!resultadoPagamento) {
            pagamento.setStatusPagamento(StatusPagamento.RECUSADO);
            pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);

            pagamentoRepository.save(pagamento);
            pedidoRepository.save(pedido);

            return pagamento;
        }

        pagamento.setStatusPagamento(StatusPagamento.APROVADO);

        realizarBaixaDeEstoque(pedido);
        calculadoraFidelidadeService.pontuarCliente(pedido);

        pagamentoRepository.save(pagamento);

        pedido.setStatusPedido(StatusPedido.COZINHA);
        pedidoRepository.save(pedido);

        return pagamento;
    }

    private void realizarBaixaDeEstoque(Pedido pedido) {
        List<ErroEstoqueDTO> erros = new ArrayList<>();

        for (ItemPedido item : pedido.getItens()) {
            ItemEstoque estoque = itemEstoqueRepository
                    .findByUnidadeIdAndProdutoId(pedido.getUnidade().getId(), item.getProduto().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Estoque não encontrado no sistema da unidade."));

            if (estoque.getQuantidade() < item.getQuantidade()) {
                erros.add(new ErroEstoqueDTO(
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        estoque.getQuantidade()
                ));
            } else {
                estoque.setQuantidade(estoque.getQuantidade() - item.getQuantidade());
                itemEstoqueRepository.save(estoque);
            }
        }

        if (!erros.isEmpty()) {
            throw new EstoqueInsuficienteException(erros);
        }
    }
}
