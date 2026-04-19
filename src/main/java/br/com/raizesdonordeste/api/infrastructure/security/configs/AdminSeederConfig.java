package br.com.raizesdonordeste.api.infrastructure.security.configs;

import br.com.raizesdonordeste.api.domain.unidade.Unidade;
import br.com.raizesdonordeste.api.domain.usuario.Usuario;
import br.com.raizesdonordeste.api.domain.usuario.enums.Role;
import br.com.raizesdonordeste.api.repository.UnidadeRepository;
import br.com.raizesdonordeste.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminSeederConfig implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final UnidadeRepository unidadeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!usuarioRepository.existsByLogin("admin@raizes.com")) {

            Usuario admin = new Usuario();
            admin.setLogin("admin@raizes.com");
            admin.setSenha(passwordEncoder.encode("123456"));
            admin.setRole(Role.ADMIN);

            usuarioRepository.save(admin);
            System.out.println("✅ Usuário ADMIN padrão criado com sucesso!");
        }
    }
}