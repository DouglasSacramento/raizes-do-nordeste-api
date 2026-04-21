package br.com.raizesdonordeste.api.domain.unidade;

import br.com.raizesdonordeste.api.domain.produto.Produto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "itens_estoque")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ItemEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    @Version
    private Long versao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;
}
