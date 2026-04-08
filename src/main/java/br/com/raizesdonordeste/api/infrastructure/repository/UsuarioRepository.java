package br.com.raizesdonordeste.api.infrastructure.repository;

import br.com.raizesdonordeste.api.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
