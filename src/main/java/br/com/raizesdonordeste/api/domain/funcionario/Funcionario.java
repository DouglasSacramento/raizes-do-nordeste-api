package br.com.raizesdonordeste.api.domain.funcionario;

import br.com.raizesdonordeste.api.domain.pessoa.Pessoa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "funcionarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Funcionario extends Pessoa {

    private String identificacao;

    @Column(name = "data_admissao")
    private LocalDate dataAdmissao;
}
