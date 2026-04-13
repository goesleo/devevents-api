package com.devevents.api.controller;

import com.devevents.api.dto.RegistrationRequestDTO;
import com.devevents.api.dto.RegistrationResponseDTO;
import com.devevents.api.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequestDTO dto) {
        try {
            RegistrationResponseDTO response = registrationService.register(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            // Retorna erro 400 (Bad Request) se cair em alguma regra de negócio (ex: evento não existe)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}