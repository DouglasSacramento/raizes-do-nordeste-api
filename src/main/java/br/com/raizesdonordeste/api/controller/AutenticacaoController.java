package br.com.raizesdonordeste.api.controller;

import br.com.raizesdonordeste.api.domain.usuario.Usuario;
import br.com.raizesdonordeste.api.service.autenticacao.dto.DadosLoginDTO;
import br.com.raizesdonordeste.api.service.autenticacao.dto.TokenResponseDTO;
import br.com.raizesdonordeste.api.infrastructure.security.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticações", description = "Operações relacionadas às autenticações")
public class AutenticacaoController {

    private final TokenService tokenService;
    private final AuthenticationManager manager;

    public AutenticacaoController(TokenService tokenService, AuthenticationManager manager) {
        this.tokenService = tokenService;
        this.manager = manager;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> logar(@RequestBody @Valid DadosLoginDTO dados) {

        var tokenDeAutenticacao = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(tokenDeAutenticacao);

        var usuarioLogado = (Usuario) authentication.getPrincipal();
        var tokenJWT = tokenService.gerarToken(usuarioLogado);

        return ResponseEntity.ok().body(new TokenResponseDTO(tokenJWT));
    }
}