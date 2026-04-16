package br.com.raizesdonordeste.api.repository;

import br.com.raizesdonordeste.api.domain.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
