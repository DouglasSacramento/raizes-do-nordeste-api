package br.com.raizesdonordeste.api.repository;

import br.com.raizesdonordeste.api.domain.produto.Produto;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByNome(String nome);
}
