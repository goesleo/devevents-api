package com.devevents.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import com.devevents.api.model.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // O Spring vai lá no application.properties e injeta o valor aqui
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            // Define o algoritmo de criptografia usando a nossa senha mestre
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create().withIssuer("devevents-api") // Quem está emitindo esse token?
                    .withSubject(user.getUsername()) // Qual é o "dono" do token? (O e-mail dele)
                    .withExpiresAt(genExpirationDate()) // Quando o token vence?
                    .sign(algorithm); // Assina e finaliza
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).withIssuer("devevents-api").build().verify(token).getSubject(); // Devolve o e-mail que estava guardado dentro do token
        } catch (JWTVerificationException exception) {
            // Se o token for inválido, expirado ou falso, cai aqui e retorna vazio
            return "";
        }
    }

    // Regra de negócio: O token dura 2 horas
    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); // Fuso horário do Brasil (Brasília)
    }
}
