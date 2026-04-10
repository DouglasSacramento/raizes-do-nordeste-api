package br.com.raizesdonordeste.api.infrastructure.controller.pedido;

import br.com.raizesdonordeste.api.application.service.PedidoService;
import br.com.raizesdonordeste.api.application.service.dto.PedidoRequestDTO;
import br.com.raizesdonordeste.api.application.service.dto.PedidoResponseDTO;
import br.com.raizesdonordeste.api.application.service.dto.PedidoStatusRequestDTO;
import br.com.raizesdonordeste.api.application.service.dto.PedidoStatusResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criar(@Valid @RequestBody PedidoRequestDTO dados, UriComponentsBuilder uriBuilder) {
        var pedido = pedidoService.criarPedido(dados);
        URI uri = uriBuilder.path("/api/v1/pedidos/{id}").buildAndExpand(pedido.getId()).toUri();

        return ResponseEntity.created(uri).body(new PedidoResponseDTO(pedido));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PedidoStatusResponseDTO> atualizarStatus(@PathVariable Long id,@Valid @RequestBody PedidoStatusRequestDTO dados) {
        var pedido = pedidoService.atualizarStatus(id, dados.novoStatus());

        return ResponseEntity.ok().body(new PedidoStatusResponseDTO(pedido));
    }
}
