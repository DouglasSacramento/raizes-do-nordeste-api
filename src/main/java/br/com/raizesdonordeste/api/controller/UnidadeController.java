package br.com.raizesdonordeste.api.controller;

import br.com.raizesdonordeste.api.service.unidade.UnidadeService;
import br.com.raizesdonordeste.api.service.unidade.dto.UnidadeRequestDTO;
import br.com.raizesdonordeste.api.service.unidade.dto.UnidadeResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/unidades")
@RequiredArgsConstructor
public class UnidadeController {

    private final UnidadeService unidadeService;

    @PostMapping
    public ResponseEntity<UnidadeResponseDTO> registrar(@Valid @RequestBody UnidadeRequestDTO dados){
        var unidadeNova = unidadeService.registrar(dados);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(unidadeNova.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new UnidadeResponseDTO(unidadeNova));
    }

}
