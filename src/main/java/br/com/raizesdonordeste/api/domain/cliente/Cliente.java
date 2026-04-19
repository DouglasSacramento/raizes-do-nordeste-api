package br.com.raizesdonordeste.api.domain.cliente;

import br.com.raizesdonordeste.api.domain.pessoa.Pessoa;
import br.com.raizesdonordeste.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "clientes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cliente extends Pessoa {

    private Integer pontos;

    @Column(name = "aceite_lgpd")
    private Boolean aceiteLgpd = false;
}
