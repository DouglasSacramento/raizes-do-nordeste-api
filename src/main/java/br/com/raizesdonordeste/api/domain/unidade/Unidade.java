package br.com.raizesdonordeste.api.domain.unidade;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "unidades")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;

    @OneToMany(mappedBy = "unidade", cascade = CascadeType.ALL)
    private List<ItemEstoque> estoque;
}
