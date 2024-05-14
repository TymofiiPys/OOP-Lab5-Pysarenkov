package com.restaurant.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.restaurant.entity.Client;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

public class JWTFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String token = request.getHeader("access-token");
        Algorithm algorithm = Algorithm.HMAC256("secretlysecret");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("IMBARESTAURANT")
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        if (decodedJWT.getExpiresAt().before(new Date())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        Long id = decodedJWT.getClaim("id").asLong();
        String email = decodedJWT.getClaim("email").asString();
        boolean isAdmin = decodedJWT.getClaim("isAdmin").asBoolean();
        Client client = new Client();
        client.setId(id);
        client.setEmail(email);
        client.setAdmin(isAdmin);
        request.setAttribute("client", client);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return "/lab4/auth".equals(path);
    }
}