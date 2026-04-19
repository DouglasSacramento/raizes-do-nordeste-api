package br.com.raizesdonordeste.api.domain.cliente;

import br.com.raizesdonordeste.api.domain.pessoa.Pessoa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cliente extends Pessoa {

    private Integer pontos = 0;

    @Column(name = "aceite_lgpd")
    private Boolean aceiteLgpd;
}
