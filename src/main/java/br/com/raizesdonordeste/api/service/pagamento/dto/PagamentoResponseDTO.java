package br.com.raizesdonordeste.api.service.pagamento.dto;

import br.com.raizesdonordeste.api.domain.pagamento.Pagamento;
import br.com.raizesdonordeste.api.domain.pagamento.enums.StatusPagamento;

public record PagamentoResponseDTO(
        Long pedidoId,
        String codigoGateway,
        StatusPagamento statusPagamento
) {
    public PagamentoResponseDTO(Pagamento pagamento) {
        this(pagamento.getPedido().getId(), pagamento.getCodigoGateway(), pagamento.getStatusPagamento());
    }
}
