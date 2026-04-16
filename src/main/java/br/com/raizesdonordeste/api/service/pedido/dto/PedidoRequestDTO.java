package br.com.raizesdonordeste.api.application.service.pedido.dto;

import br.com.raizesdonordeste.api.domain.pagamento.enums.MetodoPagamento;
import br.com.raizesdonordeste.api.domain.pedido.enums.CanalPedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequestDTO(
        @NotNull
        Long unidadeId,

        @NotNull
        Long clienteId,

        @NotNull
        Boolean exigeTroco,

        @NotNull
        CanalPedido canal,

        @NotNull
        MetodoPagamento metodoPagamento,

        @Valid
        @NotEmpty
        List<ItemCarrinhoRequestDTO> carrinho
) {
}

