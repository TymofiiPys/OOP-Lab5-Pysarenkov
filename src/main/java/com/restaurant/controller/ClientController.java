package com.restaurant.controller;

import com.restaurant.dto.AuthToken;
import com.restaurant.dto.LoginDTO;
import com.restaurant.service.ClientService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<AuthToken> auth(
            @RequestParam(name = "signup", required = false) String signup,
            @RequestBody LoginDTO loginDTO
    ) {
        if (signup != null) {
            AuthToken token = clientService.signup(loginDTO);
            if (token.getStatus() < 0) {
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity.ok(token);
            }
        } else {
            if (loginDTO.getEmail() == null || loginDTO.getPassword() == null) {
                return ResponseEntity.badRequest().build();
            }
            AuthToken token = clientService.authenticate(loginDTO);
            if (token.getStatus() < 0) {
                return ResponseEntity.notFound().build();
            } else if (token.getStatus() == 1) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } else {
                return ResponseEntity.ok(token);
            }
        }
    }
}
