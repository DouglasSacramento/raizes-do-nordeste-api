package br.com.raizesdonordeste.api.controller;

import br.com.raizesdonordeste.api.service.produto.ProdutoService;
import br.com.raizesdonordeste.api.service.produto.dto.ProdutoDetalhamentoResponseDTO;
import br.com.raizesdonordeste.api.service.produto.dto.ProdutoRequestDTO;
import br.com.raizesdonordeste.api.service.produto.dto.ProdutoResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrar(@Valid @RequestBody ProdutoRequestDTO dados) {
        var produtoNovo = produtoService.cadastrar(dados);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produtoNovo.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new ProdutoResponseDTO(produtoNovo));
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDTO>> listarTodos(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var paginaDeProdutos = produtoService.listarTodos(pageable);

        return ResponseEntity.ok().body(paginaDeProdutos);
    }

    @GetMapping("/{produtoID}")
    public ResponseEntity<ProdutoDetalhamentoResponseDTO> buscarPorId(@PathVariable Long produtoID) {
        var produto = produtoService.buscarPorId(produtoID);

        return ResponseEntity.ok().body(new ProdutoDetalhamentoResponseDTO(produto));
    }
}
