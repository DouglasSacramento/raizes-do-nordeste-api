package br.com.raizesdonordeste.api.domain.pagamento;

import br.com.raizesdonordeste.api.domain.pagamento.enums.MetodoPagamento;
import br.com.raizesdonordeste.api.domain.pagamento.enums.StatusPagamento;
import br.com.raizesdonordeste.api.domain.pedido.Pedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    private BigDecimal valor;

    @Column(name = "codigo_gateway")
    private String codigoGateway;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento")
    private MetodoPagamento metodoPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento")
    private StatusPagamento statusPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}
