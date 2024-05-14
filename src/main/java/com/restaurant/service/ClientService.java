package com.restaurant.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.restaurant.dto.AuthToken;
import com.restaurant.dto.LoginDTO;
import com.restaurant.entity.Client;
import com.restaurant.entity.Password;
import com.restaurant.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public AuthToken authenticate(LoginDTO loginDTO) {
        Optional<Client> clientToAuth = clientRepository.findByEmail(loginDTO.getEmail());
        if (clientToAuth.isEmpty()) {
            return new AuthToken(-1, "");
        }
        Password password = Password.builder()
                .hash(clientToAuth.get().getHash())
                .salt(clientToAuth.get().getSalt())
                .build();
        String hashedPwdLogin = BCrypt.hashpw(loginDTO.getPassword(), password.getSalt());
        if(!hashedPwdLogin.equals(password.getHash())) {
            return new AuthToken(1, "");
        }
        Client authedClient = clientToAuth.get();
        Algorithm algorithm = Algorithm.HMAC256("secretlysecret");
        String jwt = JWT.create()
                .withIssuer("IMBARESTAURANT")
                .withClaim("id", authedClient.getId())
                .withClaim("email", authedClient.getEmail())
                .withClaim("isAdmin", authedClient.isAdmin())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000L))
                .sign(algorithm);
        return new AuthToken(0, jwt);
    }

    public AuthToken signup(LoginDTO loginDTO) {
        Client newClient = new Client();
        newClient.setEmail(loginDTO.getEmail());
        newClient.setAdmin(false);
        String salt = BCrypt.gensalt();
        String hash = BCrypt.hashpw(loginDTO.getPassword(), salt);
        newClient.setHash(hash);
        newClient.setSalt(salt);
        Client createdClient = clientRepository.save(newClient);
//        if (createdClient == null) {
//            return new AuthToken(-1, "");
//        }

        Algorithm algorithm = Algorithm.HMAC256("secretlysecret");
        String jwt = JWT.create()
                .withIssuer("IMBARESTAURANT")
                .withClaim("id", createdClient.getId())
                .withClaim("email", createdClient.getEmail())
                .withClaim("isAdmin", createdClient.isAdmin())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000L))
                .sign(algorithm);
        return new AuthToken(0, jwt);
    }
}
