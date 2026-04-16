package br.com.raizesdonordeste.api.service.pagamento;

import br.com.raizesdonordeste.api.domain.pagamento.Pagamento;
import br.com.raizesdonordeste.api.domain.pagamento.enums.StatusPagamento;
import br.com.raizesdonordeste.api.domain.pedido.Pedido;
import br.com.raizesdonordeste.api.domain.pedido.enums.StatusPedido;
import br.com.raizesdonordeste.api.infrastructure.exception.exceptions.PagamentoStatusInvalidoException;
import br.com.raizesdonordeste.api.repository.PagamentoRepository;
import br.com.raizesdonordeste.api.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;

    @Transactional
    public Pagamento processarPagamento(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido informado não existe."));

        List<StatusPedido> statusPermitidos = List.of(StatusPedido.RECEBIDO);
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
        pagamento.setStatusPagamento(StatusPagamento.APROVADO);
        pagamento.setPedido(pedido);

        pagamentoRepository.save(pagamento);

        pedido.setStatusPedido(StatusPedido.COZINHA);
        pedidoRepository.save(pedido);

        return pagamento;
    }
}
