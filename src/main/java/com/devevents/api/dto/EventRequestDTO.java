package com.devevents.api.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EventRequestDTO(
        @NotBlank(message = "O titulo é obrigatorio")
        String title,

        @NotBlank(message = "A descrição é obrigatoria")
        String description,

        @NotNull(message = "A data de início é obrigatória")
        @FutureOrPresent(message = "A data não pode estar no passado")
        LocalDateTime startDate,

        @NotNull(message = "A data de término é obrigatória")
        LocalDateTime endDate,

        String location

        ) {
}
