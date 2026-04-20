package br.com.raizesdonordeste.api.controller;

import br.com.raizesdonordeste.api.domain.unidade.ItemEstoque;
import br.com.raizesdonordeste.api.domain.usuario.Usuario;
import br.com.raizesdonordeste.api.service.estoque.EstoqueService;
import br.com.raizesdonordeste.api.service.estoque.dto.EstoqueRequestDTO;
import br.com.raizesdonordeste.api.service.estoque.dto.EstoqueResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/estoque")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping("/abastecer")
    public ResponseEntity<EstoqueResponseDTO> abastecerEstoque(@AuthenticationPrincipal Usuario usuario, @Valid @RequestBody EstoqueRequestDTO dados) {
        ItemEstoque estoqueNovo = estoqueService.abastecerEstoque(usuario.getId(), dados);

        return ResponseEntity.ok().body(new EstoqueResponseDTO(estoqueNovo));
    }
}
