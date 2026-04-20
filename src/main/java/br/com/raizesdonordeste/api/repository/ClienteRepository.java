package br.com.raizesdonordeste.api.repository;

import br.com.raizesdonordeste.api.domain.cliente.Cliente;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCpf(String cpf);

    Optional<Cliente> findByUsuarioId(Long id);

    Optional<Cliente> findByCpf(String cpf);
}
