package com.devevents.api.controller;

import com.devevents.api.dto.EventRequestDTO;
import com.devevents.api.dto.EventResponseDTO;
import com.devevents.api.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    @PostMapping
    public ResponseEntity<EventResponseDTO> saveEvent(@RequestBody EventRequestDTO dto) {
        EventResponseDTO response = eventService.createEvent(dto);
        // Retorna o status 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getActiveEvents() {
        List<EventResponseDTO> responses = eventService.getActiveEvents();
        // Retorna o status 200 (OK) com a lista de eventos
        return ResponseEntity.ok(responses);
    }

}
