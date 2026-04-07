package br.com.raizesdonordeste.api.infrastructure.repository;

import br.com.raizesdonordeste.api.domain.pedido.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
