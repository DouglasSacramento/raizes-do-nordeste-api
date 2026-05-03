package br.com.raizesdonordeste.api.controller;

import br.com.raizesdonordeste.api.service.funcionario.FuncionarioService;
import br.com.raizesdonordeste.api.service.funcionario.dto.FuncionarioRequestDTO;
import br.com.raizesdonordeste.api.service.funcionario.dto.FuncionarioResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/funcionarios")
@RequiredArgsConstructor
@Tag(name = "Funcionários", description = "Operações relacionadas aos funcionários")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping("/registrar")
    public ResponseEntity<FuncionarioResponseDTO> registrar(@Valid @RequestBody FuncionarioRequestDTO dados) {
        var funcionarioNovo = funcionarioService.registrar(dados);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(funcionarioNovo.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new FuncionarioResponseDTO(funcionarioNovo));
    }
}
