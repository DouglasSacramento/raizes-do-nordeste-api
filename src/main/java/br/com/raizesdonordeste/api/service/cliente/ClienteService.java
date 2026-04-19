package br.com.raizesdonordeste.api.service.cliente;

import br.com.raizesdonordeste.api.domain.cliente.Cliente;
import br.com.raizesdonordeste.api.domain.usuario.Usuario;
import br.com.raizesdonordeste.api.domain.usuario.enums.Role;
import br.com.raizesdonordeste.api.infrastructure.exception.exceptions.RecursoJaCadastradoException;
import br.com.raizesdonordeste.api.repository.ClienteRepository;
import br.com.raizesdonordeste.api.repository.UsuarioRepository;
import br.com.raizesdonordeste.api.service.cliente.dto.ClienteRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Cliente cadastrar(ClienteRequestDTO dados){
        if (usuarioRepository.findByLogin(dados.login()) != null){
            throw new RecursoJaCadastradoException("Este email já está cadastrado no sistema!");
        }

        if (clienteRepository.existsByCpf(dados.cpf())){
            throw new RecursoJaCadastradoException("Este cpf já está cadastrado no sistema!");
        }

        Usuario usuarioNovo = new Usuario();
        usuarioNovo.setLogin(dados.login());
        usuarioNovo.setSenha(passwordEncoder.encode(dados.senha()));
        usuarioNovo.setRole(Role.CLIENTE);

        usuarioRepository.save(usuarioNovo);

        Cliente clienteNovo = new Cliente();
        clienteNovo.setNome(dados.nome());
        clienteNovo.setCpf(dados.cpf());
        clienteNovo.setDataNasc(dados.dataNasc());
        clienteNovo.setUsuario(usuarioNovo);

        return clienteRepository.save(clienteNovo);
    }
}
