package br.com.raizesdonordeste.api.domain.usuario.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum Role {
    ADMIN("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_ATENDENTE", "ROLE_COZINHA", "ROLE_CLIENTE"),
    GERENTE("ROLE_GERENTE", "ROLE_ATENDENTE", "ROLE_COZINHA", "ROLE_CLIENTE"),
    ATENDENTE("ROLE_ATENDENTE", "ROLE_CLIENTE"),
    COZINHA("ROLE_COZINHA", "ROLE_CLIENTE"),
    CLIENTE("ROLE_CLIENTE");

    private final List<String> permissoes;

    Role(String... permissoes) {
        this.permissoes = List.of(permissoes);
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return this.permissoes.stream()
                .map(permissao -> new SimpleGrantedAuthority(permissao))
                .toList();
    }
}
