package com.devevents.api.controller;

import com.devevents.api.dto.AuthenticationDTO;
import com.devevents.api.dto.LoginResponseDTO;
import com.devevents.api.dto.RegisterDTO;
import com.devevents.api.repository.UserRepository;
import com.devevents.api.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.devevents.api.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((com.devevents.api.model.User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
        if (this.userRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = User.builder()
                .email(data.email())
                .password(encryptedPassword)
                .role(data.role())
                .build();

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
