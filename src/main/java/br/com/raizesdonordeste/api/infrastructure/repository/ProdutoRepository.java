package br.com.raizesdonordeste.api.infrastructure.repository;

import br.com.raizesdonordeste.api.domain.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
