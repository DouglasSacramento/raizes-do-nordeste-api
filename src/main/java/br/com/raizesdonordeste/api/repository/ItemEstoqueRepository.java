package br.com.raizesdonordeste.api.repository;

import br.com.raizesdonordeste.api.domain.unidade.ItemEstoque;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long> {
}
