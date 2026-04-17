package br.com.raizesdonordeste.api.service.unidade;

import br.com.raizesdonordeste.api.domain.unidade.Unidade;
import br.com.raizesdonordeste.api.infrastructure.exception.exceptions.RecursoJaCadastradoException;
import br.com.raizesdonordeste.api.repository.UnidadeRepository;
import br.com.raizesdonordeste.api.service.unidade.dto.UnidadeRequestDTO;
import br.com.raizesdonordeste.api.service.unidade.dto.UnidadeResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;

    @Transactional
    public Unidade registrar(UnidadeRequestDTO dados){
        if (unidadeRepository.existsByNome(dados.nome())){
            throw new RecursoJaCadastradoException("Unidade já cadastrada no sistema.");
        }

        Unidade unidadeNova = new Unidade();
        unidadeNova.setNome(dados.nome());
        unidadeNova.setTelefone(dados.telefone());

        return unidadeRepository.save(unidadeNova);
    }

}
