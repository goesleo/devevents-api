package com.devevents.api.controller;

import com.devevents.api.dto.RegistrationRequestDTO;
import com.devevents.api.dto.RegistrationResponseDTO;
import com.devevents.api.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/event/{eventId}")
    public ResponseEntity<?> getEventRegistrations(@PathVariable UUID eventId) {
        try {
            List<RegistrationResponseDTO> responses = registrationService.getEventRegistrations(eventId);
            return ResponseEntity.ok(responses); // Retorna 200 OK com a lista
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}