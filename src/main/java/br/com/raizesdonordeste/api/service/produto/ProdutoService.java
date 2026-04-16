package br.com.raizesdonordeste.api.service.produto;

import br.com.raizesdonordeste.api.domain.produto.Produto;
import br.com.raizesdonordeste.api.infrastructure.exception.exceptions.RecursoJaCadastradoException;
import br.com.raizesdonordeste.api.repository.ProdutoRepository;
import br.com.raizesdonordeste.api.service.produto.dto.ProdutoDetalhamentoResponseDTO;
import br.com.raizesdonordeste.api.service.produto.dto.ProdutoRequestDTO;
import br.com.raizesdonordeste.api.service.produto.dto.ProdutoResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Produto cadastrar(ProdutoRequestDTO dados) {
        if (produtoRepository.existsByNome(dados.nome())) {
            throw new RecursoJaCadastradoException("O produto(" + dados.nome() + ") já está cadastrado no sistema.");
        }

        Produto produtoNovo = new Produto();
        produtoNovo.setNome(dados.nome());
        produtoNovo.setDescricao(dados.descricao());
        produtoNovo.setPrecoAtual(dados.precoAtual());

        return produtoRepository.save(produtoNovo);
    }

    public List<ProdutoResponseDTO> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream()
                .map(ProdutoResponseDTO::new)
                .toList();
    }

    public Produto buscarPorId(Long produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
    }
}
