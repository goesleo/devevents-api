package com.devevents.api.dto;

import com.devevents.api.model.Registration;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegistrationResponseDTO(
        UUID registrationId,
        String eventTitle,
        String participantName,
        LocalDateTime registrationDate
) {
    public RegistrationResponseDTO(Registration registration) {
        this(
                registration.getId(),
                registration.getEvent().getTitle(),
                registration.getParticipant().getName(),
                registration.getRegistrationDate()
        );
        }
    }