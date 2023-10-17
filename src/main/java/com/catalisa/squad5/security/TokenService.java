package com.catalisa.squad5.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.catalisa.squad5.model.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${security.token.secret}")
    private  String secretKey;

    public String createToken(Users users) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            String token = JWT.create().withIssuer("squad5")
                    .withSubject(users.getUsername())
                    .withExpiresAt(expiresTokenDate())
                    .sign(algorithm);

            return token;
        }catch (JWTCreationException exception){
            throw  new RuntimeException("Erro ao gerar token", exception);
        }

    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm).withIssuer("squad").build().verify(token).getSubject();
        }catch (JWTVerificationException exception){
            exception.printStackTrace();
            return "";
        }
    }

    private Instant expiresTokenDate() {
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }
}
