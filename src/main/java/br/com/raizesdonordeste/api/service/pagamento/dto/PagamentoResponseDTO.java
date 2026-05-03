package br.com.raizesdonordeste.api.service.pagamento.dto;

import br.com.raizesdonordeste.api.domain.pagamento.Pagamento;
import br.com.raizesdonordeste.api.domain.pagamento.enums.StatusPagamento;
import br.com.raizesdonordeste.api.domain.pedido.enums.StatusPedido;

public record PagamentoResponseDTO(
        Long pedidoId,
        String codigoGateway,
        StatusPagamento statusPagamento,
        StatusPedido statusPedido
) {
    public PagamentoResponseDTO(Pagamento pagamento) {
        this(pagamento.getPedido().getId(), pagamento.getCodigoGateway(), pagamento.getStatusPagamento(), pagamento.getPedido().getStatusPedido());
    }
}
