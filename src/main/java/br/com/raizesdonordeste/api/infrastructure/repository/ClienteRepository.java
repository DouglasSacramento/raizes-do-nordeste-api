package br.com.raizesdonordeste.api.infrastructure.repository;

import br.com.raizesdonordeste.api.domain.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
