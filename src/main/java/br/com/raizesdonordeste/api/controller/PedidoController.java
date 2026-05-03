package br.com.raizesdonordeste.api.controller;

import br.com.raizesdonordeste.api.domain.pedido.enums.CanalPedido;
import br.com.raizesdonordeste.api.domain.usuario.Usuario;
import br.com.raizesdonordeste.api.service.pedido.PedidoService;
import br.com.raizesdonordeste.api.service.pedido.dto.PedidoRequestDTO;
import br.com.raizesdonordeste.api.service.pedido.dto.PedidoResponseDTO;
import br.com.raizesdonordeste.api.service.pedido.dto.PedidoStatusRequestDTO;
import br.com.raizesdonordeste.api.service.pedido.dto.PedidoStatusResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "Operações relacionadas aos pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @PreAuthorize("hasAnyRole('CLIENTE', 'GERENTE')")
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criar(@AuthenticationPrincipal Usuario usuario, @Valid @RequestBody PedidoRequestDTO dados) {
        var pedido = pedidoService.criarPedido(usuario, dados);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pedido.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new PedidoResponseDTO(pedido));
    }

    @PreAuthorize("hasRole('ATENDENTE')")
    @PatchMapping("/{id}")
    public ResponseEntity<PedidoStatusResponseDTO> atualizarStatus(@PathVariable Long id, @Valid @RequestBody PedidoStatusRequestDTO dados) {
        var pedido = pedidoService.atualizarStatus(id, dados.novoStatus());

        return ResponseEntity.ok().body(new PedidoStatusResponseDTO(pedido));
    }

    @PreAuthorize("hasRole('GERENTE')")
    @GetMapping
    public ResponseEntity<Page<PedidoResponseDTO>> listarPedidosPorCanal(
            @Valid @RequestParam(required = false) CanalPedido canalPedido,
            @PageableDefault(value = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        var pedidos = pedidoService.listarPorCanalPedido(canalPedido, pageable);

        return ResponseEntity.ok().body(pedidos);
    }
}
