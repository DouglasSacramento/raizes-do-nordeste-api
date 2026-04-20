package br.com.raizesdonordeste.api.service.funcionario;

import br.com.raizesdonordeste.api.domain.cliente.Cliente;
import br.com.raizesdonordeste.api.domain.funcionario.Funcionario;
import br.com.raizesdonordeste.api.domain.unidade.Unidade;
import br.com.raizesdonordeste.api.domain.usuario.Usuario;
import br.com.raizesdonordeste.api.infrastructure.exception.exceptions.RecursoJaCadastradoException;
import br.com.raizesdonordeste.api.repository.ClienteRepository;
import br.com.raizesdonordeste.api.repository.FuncionarioRepository;
import br.com.raizesdonordeste.api.repository.UnidadeRepository;
import br.com.raizesdonordeste.api.repository.UsuarioRepository;
import br.com.raizesdonordeste.api.service.funcionario.dto.FuncionarioRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final UnidadeRepository unidadeRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Funcionario registrar(FuncionarioRequestDTO dados) {
        if (funcionarioRepository.existsByCpf(dados.cpf())) {
            throw new RecursoJaCadastradoException("Este CPF já pertence a um funcionário!");
        }

        Unidade unidadeFuncionario = unidadeRepository.findById(dados.unidade())
                .orElseThrow(() -> new EntityNotFoundException("Unidade não cadastrada no sistema."));

        Usuario usuarioAtivo;
        Cliente clienteAtivo;

        Optional<Cliente> clienteExistente = clienteRepository.findByCpf(dados.cpf());

        if (clienteExistente.isPresent()) {
            clienteAtivo = clienteExistente.get();
            usuarioAtivo = clienteAtivo.getUsuario();

            usuarioAtivo.setUnidade(unidadeFuncionario);
            usuarioAtivo.setRole(dados.role());

            Boolean aceite = dados.aceiteLgpd();
            clienteAtivo.setAceiteLgpd(aceite != null && aceite);

            clienteRepository.save(clienteAtivo);
        } else {
            if (usuarioRepository.existsByLogin(dados.login())) {
                throw new RecursoJaCadastradoException("Este login já está em uso por outro usuário!");
            }

            usuarioAtivo = new Usuario();
            usuarioAtivo.setLogin(dados.login());
            usuarioAtivo.setSenha(passwordEncoder.encode(dados.senha()));
            usuarioAtivo.setUnidade(unidadeFuncionario);
            usuarioAtivo.setRole(dados.role());
            usuarioRepository.save(usuarioAtivo);

            clienteAtivo = new Cliente();
            clienteAtivo.setNome(dados.nome());
            clienteAtivo.setCpf(dados.cpf());
            clienteAtivo.setDataNasc(dados.dataNasc());

            Boolean aceite = dados.aceiteLgpd();
            clienteAtivo.setAceiteLgpd(aceite != null && aceite);

            clienteAtivo.setUsuario(usuarioAtivo);
            clienteRepository.save(clienteAtivo);
        }

        Funcionario funcionarioNovo = new Funcionario();
        funcionarioNovo.setNome(dados.nome());
        funcionarioNovo.setCpf(dados.cpf());
        funcionarioNovo.setIdentificacao(UUID.randomUUID().toString().substring(0, 10));
        funcionarioNovo.setDataNasc(dados.dataNasc());
        funcionarioNovo.setDataAdmissao(dados.dataAdmissao());
        funcionarioNovo.setAtivo(true);
        funcionarioNovo.setUsuario(usuarioAtivo);

        return funcionarioRepository.save(funcionarioNovo);
    }
}
