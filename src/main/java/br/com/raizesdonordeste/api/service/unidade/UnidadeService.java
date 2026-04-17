package br.com.raizesdonordeste.api.service.unidade;

import br.com.raizesdonordeste.api.domain.unidade.Unidade;
import br.com.raizesdonordeste.api.infrastructure.exception.exceptions.RecursoJaCadastradoException;
import br.com.raizesdonordeste.api.repository.UnidadeRepository;
import br.com.raizesdonordeste.api.service.unidade.dto.UnidadeRequestDTO;
import br.com.raizesdonordeste.api.service.unidade.dto.UnidadeResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;

    @Transactional
    public Unidade registrar(UnidadeRequestDTO dados) {
        if (unidadeRepository.existsByNome("Raízes do Nordeste - " + dados.nome().toUpperCase())) {
            throw new RecursoJaCadastradoException("Unidade(" + dados.nome() + ") já está cadastrada no sistema.");
        }

        Unidade unidadeNova = new Unidade();
        unidadeNova.setNome("Raízes do Nordeste - " + dados.nome().toUpperCase());
        unidadeNova.setTelefone(dados.telefone());

        return unidadeRepository.save(unidadeNova);
    }

    public Page<UnidadeResponseDTO> listarTodas(Pageable pageable) {
        return unidadeRepository.findAll(pageable)
                .map(UnidadeResponseDTO::new);
    }

    public Unidade listarPorId(Long id) {
        return unidadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unidade(" + id + ") não encontrada no sistema."));
    }

    @Transactional
    public void deletar(String nome) {
        if (!unidadeRepository.existsByNome("Raízes do Nordeste - " + nome.toUpperCase())) {
            throw new EntityNotFoundException("Unidade(" + nome + ") não está cadastrada no sistema.");
        }
        unidadeRepository.deleteByNome("Raízes do Nordeste - " + nome.toUpperCase());
    }
}
