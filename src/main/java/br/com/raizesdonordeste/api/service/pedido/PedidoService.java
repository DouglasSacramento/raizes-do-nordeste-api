package br.com.raizesdonordeste.api.service.pedido;

import br.com.raizesdonordeste.api.domain.usuario.Usuario;
import br.com.raizesdonordeste.api.repository.*;
import br.com.raizesdonordeste.api.service.pedido.dto.ItemCarrinhoRequestDTO;
import br.com.raizesdonordeste.api.service.pedido.dto.PedidoRequestDTO;
import br.com.raizesdonordeste.api.domain.cliente.Cliente;
import br.com.raizesdonordeste.api.domain.pagamento.enums.MetodoPagamento;
import br.com.raizesdonordeste.api.domain.pedido.ItemPedido;
import br.com.raizesdonordeste.api.domain.pedido.Pedido;
import br.com.raizesdonordeste.api.domain.pedido.enums.StatusPedido;
import br.com.raizesdonordeste.api.domain.produto.Produto;
import br.com.raizesdonordeste.api.domain.unidade.Unidade;
import br.com.raizesdonordeste.api.infrastructure.exception.exceptions.TransicaoStatusInvalidaException;
import br.com.raizesdonordeste.api.infrastructure.exception.exceptions.PedidoSolicitacaoTrocoIndevidaException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final UnidadeRepository unidadeRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public Pedido criarPedido(Usuario usuario, PedidoRequestDTO dados) {
        if (dados.exigeTroco() && dados.metodoPagamento() != MetodoPagamento.DINHEIRO) {
            throw new PedidoSolicitacaoTrocoIndevidaException("Troco só pode ser solicitado para pagamentos em DINHEIRO.");
        }

        Pedido novoPedido = new Pedido();
        Unidade unidade = unidadeRepository.findById(dados.unidadeId())
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada."));
        Cliente cliente = clienteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado."));

        System.out.println(cliente);

        novoPedido.setExigeTroco(dados.exigeTroco());
        novoPedido.setCanalPedido(dados.canal());
        novoPedido.setMetodoPagamento(dados.metodoPagamento());
        novoPedido.setUnidade(unidade);
        novoPedido.setCliente(cliente);
        novoPedido.setStatusPedido(StatusPedido.RECEBIDO);

        processarCarrinhoEValorTotal(novoPedido, dados.carrinho());

        return pedidoRepository.save(novoPedido);
    }

    private void processarCarrinhoEValorTotal(Pedido pedido, List<ItemCarrinhoRequestDTO> carrinhoDto) {
        BigDecimal valorTotal = BigDecimal.ZERO;
        List<ItemPedido> itensDoPedido = new ArrayList<>();

        for (ItemCarrinhoRequestDTO itemDto : carrinhoDto) {
            Produto produto = produtoRepository.findById(itemDto.produtoId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado no sistema."));

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDto.quantidade());
            item.setPrecoUnitario(produto.getPrecoAtual());
            item.setPedido(pedido);

            BigDecimal subtotal = produto.getPrecoAtual().multiply(new BigDecimal(itemDto.quantidade()));
            valorTotal = valorTotal.add(subtotal);

            itensDoPedido.add(item);
        }
        pedido.setItens(itensDoPedido);
        pedido.setValorTotal(valorTotal);
    }

    @Transactional
    public Pedido atualizarStatus(Long pedidoId, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado no sistema."));
        StatusPedido atualStatus = pedido.getStatusPedido();

        if (atualStatus == StatusPedido.CANCELADO || atualStatus == StatusPedido.ENTREGUE) {
            throw new TransicaoStatusInvalidaException("Pedido indisponivel.");
        }

        if (atualStatus == StatusPedido.RECEBIDO && !(novoStatus == StatusPedido.COZINHA || novoStatus == StatusPedido.CANCELADO)) {
            throw new TransicaoStatusInvalidaException("Pedido RECEBIDO só pode ser alterado para COZINHA ou CANCELADO.");
        }

        if (atualStatus == StatusPedido.COZINHA && !(novoStatus == StatusPedido.PRONTO || novoStatus == StatusPedido.CANCELADO)) {
            throw new TransicaoStatusInvalidaException("Pedido COZINHA só pode ser alterado para PRONTO ou CANCELADO.");
        }

        if (atualStatus == StatusPedido.PRONTO && !(novoStatus == StatusPedido.ENTREGUE || novoStatus == StatusPedido.CANCELADO)) {
            throw new TransicaoStatusInvalidaException("Pedido PRONTO só pode ser alterado para ENTREGUE ou CANCELADO.");
        }

        pedido.setStatusPedido(novoStatus);

        return pedidoRepository.save(pedido);
    }
}
