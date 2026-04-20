package br.com.raizesdonordeste.api.service.estoque;

import br.com.raizesdonordeste.api.domain.produto.Produto;
import br.com.raizesdonordeste.api.domain.unidade.ItemEstoque;
import br.com.raizesdonordeste.api.domain.unidade.Unidade;
import br.com.raizesdonordeste.api.domain.usuario.Usuario;
import br.com.raizesdonordeste.api.repository.ItemEstoqueRepository;
import br.com.raizesdonordeste.api.repository.ProdutoRepository;
import br.com.raizesdonordeste.api.repository.UnidadeRepository;
import br.com.raizesdonordeste.api.repository.UsuarioRepository;
import br.com.raizesdonordeste.api.service.estoque.dto.EstoqueRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final ItemEstoqueRepository itemEstoqueRepository;
    private final UnidadeRepository unidadeRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public ItemEstoque abastecerEstoque(Long usuarioId, EstoqueRequestDTO dados){
        Usuario usuarioLogado = usuarioRepository.findById(usuarioId)
                .orElseThrow(()-> new EntityNotFoundException("Usuário não cadastrado no sistema."));

        Optional<ItemEstoque> estoqueIncerto = itemEstoqueRepository.findByUnidadeIdAndProdutoId(usuarioLogado.getUnidade().getId(), dados.produtoId());

        if (estoqueIncerto.isPresent()){
            ItemEstoque estoqueExistente = estoqueIncerto.get();
            estoqueExistente.setQuantidade(estoqueExistente.getQuantidade() + dados.quantidadeNova());
            return itemEstoqueRepository.save(estoqueExistente);
        }

        Unidade unidade = usuarioLogado.getUnidade();

        Produto produto = produtoRepository.findById(dados.produtoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não cadastrado no sistema."));

        ItemEstoque estoqueNovo = new ItemEstoque();
        estoqueNovo.setUnidade(unidade);
        estoqueNovo.setProduto(produto);
        estoqueNovo.setQuantidade(dados.quantidadeNova());

        return itemEstoqueRepository.save(estoqueNovo);
    }
}
