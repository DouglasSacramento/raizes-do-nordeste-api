package br.com.raizesdonordeste.api.service.usuario;

import br.com.raizesdonordeste.api.infrastructure.exception.exceptions.UsuarioNaoEncontrado;
import br.com.raizesdonordeste.api.infrastructure.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void deletar(Long id){
        if (!repository.existsById(id)){
            throw new UsuarioNaoEncontrado("Usuário(ID " + id + ") não encontrado!");
        }
        repository.deleteById(id);
    }
}
