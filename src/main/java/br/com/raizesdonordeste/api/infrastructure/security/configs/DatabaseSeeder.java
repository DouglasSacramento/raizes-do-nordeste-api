package br.com.raizesdonordeste.api.infrastructure.security.configs;

import br.com.raizesdonordeste.api.domain.cliente.Cliente;
import br.com.raizesdonordeste.api.domain.produto.Produto;
import br.com.raizesdonordeste.api.domain.unidade.ItemEstoque;
import br.com.raizesdonordeste.api.domain.unidade.Unidade;
import br.com.raizesdonordeste.api.domain.usuario.Usuario;
import br.com.raizesdonordeste.api.domain.usuario.enums.Role;
import br.com.raizesdonordeste.api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UnidadeRepository unidadeRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemEstoqueRepository itemEstoqueRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (unidadeRepository.count() > 0) {
            System.out.println("Banco de dados já está populado. Pulando Seeder.");
            return;
        }

        System.out.println("Iniciando o Database Seeding (Populando o banco)...");

        Unidade matriz = new Unidade();
        matriz.setNome("Raízes do Nordeste - MATRIZ");
        matriz.setTelefone("11999999999");

        Unidade centro = new Unidade();
        centro.setNome("Raízes do Nordeste - CENTRO");
        centro.setTelefone("12999999999");
        unidadeRepository.saveAll(List.of(matriz, centro));

        Produto baiao = new Produto();
        baiao.setNome("Baião de Dois Especial");
        baiao.setDescricao("Feijão de corda, arroz, queijo coalho e carne de sol.");
        baiao.setPrecoAtual(new BigDecimal("45.90"));

        Produto tapioca = new Produto();
        tapioca.setNome("Tapioca de Carne Seca");
        tapioca.setDescricao("Com requeijão cremoso do sertão.");
        tapioca.setPrecoAtual(new BigDecimal("22.50"));

        produtoRepository.saveAll(List.of(baiao, tapioca));

        ItemEstoque estoqueBaiao = new ItemEstoque();
        estoqueBaiao.setUnidade(centro);
        estoqueBaiao.setProduto(baiao);
        estoqueBaiao.setQuantidade(2);
        estoqueBaiao.setVersao(0L);

        ItemEstoque estoqueTapioca = new ItemEstoque();
        estoqueTapioca.setUnidade(centro);
        estoqueTapioca.setProduto(tapioca);
        estoqueTapioca.setQuantidade(1);
        estoqueTapioca.setVersao(0L);

        itemEstoqueRepository.saveAll(List.of(estoqueBaiao, estoqueTapioca));

        Usuario userCliente = new Usuario();
        userCliente.setLogin("cliente@email.com");
        userCliente.setSenha(passwordEncoder.encode("123456"));
        userCliente.setRole(Role.CLIENTE);
        usuarioRepository.save(userCliente);

        Cliente cliente = new Cliente();
        cliente.setNome("Fulano da Silva");
        cliente.setCpf("12345678900");
        cliente.setDataNasc(LocalDate.of(1990, 5, 10));
        cliente.setUsuario(userCliente);
        cliente.setPontos(0);
        cliente.setAceiteLgpd(true);
        clienteRepository.save(cliente);

        System.out.println("Banco populado com sucesso! Utilize cliente@email.com / 123456 para testar.");
    }
}
