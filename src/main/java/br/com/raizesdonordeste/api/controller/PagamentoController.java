package br.com.raizesdonordeste.api.controller;

import br.com.raizesdonordeste.api.service.pagamento.PagamentoService;
import br.com.raizesdonordeste.api.domain.pagamento.Pagamento;
import br.com.raizesdonordeste.api.service.pagamento.dto.PagamentoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @PreAuthorize("hasAnyRole('CLIENTE', 'ATENDENTE')")
    @PostMapping("/pedidos/{pedidoId}/processar")
    public ResponseEntity<PagamentoResponseDTO> processar(@PathVariable Long pedidoId) {
        Pagamento pagamento = pagamentoService.processarPagamento(pedidoId);

        return ResponseEntity.ok().body(new PagamentoResponseDTO(pagamento));
    }
}
