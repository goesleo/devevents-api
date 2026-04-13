package com.devevents.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RegistrationRequestDTO(
        @NotNull(message = "O ID do evento é obrigatório")
        UUID eventId,

        @NotBlank(message = "O nome do participante é obrigatorio")
        String participantName,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        String participantEmail

) {}
