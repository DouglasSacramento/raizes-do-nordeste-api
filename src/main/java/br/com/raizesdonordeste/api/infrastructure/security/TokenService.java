package br.com.raizesdonordeste.api.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private Instant gerarDataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String gerarToken(String login){
        try {
            var algoritimo = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("API Raizes do Nordeste")
                    .withSubject(login)
                    .withExpiresAt(gerarDataExpiracao())
                    .sign(algoritimo);

        }catch (JWTCreationException exception){
            throw new RuntimeException("Erro interno ao gerar o Token JWT", exception);
        }
    }

    public String getSubject(String tokenJWT){
        try {
            var algoritimo = Algorithm.HMAC256(secret);
            return JWT.require(algoritimo)
                    .withIssuer("API Raizes do Nordeste")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        }catch (JWTVerificationException ex){
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }
}
