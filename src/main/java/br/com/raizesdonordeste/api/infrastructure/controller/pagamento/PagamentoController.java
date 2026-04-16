package br.com.raizesdonordeste.api.infrastructure.controller.pagamento;

import br.com.raizesdonordeste.api.application.service.pagamento.PagamentoService;
import br.com.raizesdonordeste.api.domain.pagamento.Pagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @PostMapping("/pedidos/{pedidoId}/processar")
    public ResponseEntity<String> processar(@PathVariable Long pedidoId){
        Pagamento pagamento = pagamentoService.processarPagamento(pedidoId);

        return ResponseEntity.ok().body("Pagamento Aprovado com sucesso! Recibo: " + pagamento.getCodigoGateway());
    }
}
