package br.com.raizesdonordeste.api.repository;

import br.com.raizesdonordeste.api.domain.pagamento.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
