package com.devevents.api.dto;

import com.devevents.api.model.Event;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventResponseDTO(
        UUID id,
        String title,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String location,
        boolean active
) {

    public EventResponseDTO(Event event) {
        this(event.getId(), event.getTitle(), event.getDescription(),
                event.getStartDate(), event.getEndDate(), event.getLocation(), event.isActive());
    }
}