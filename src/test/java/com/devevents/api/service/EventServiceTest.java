package com.devevents.api.service;

import com.devevents.api.dto.EventRequestDTO;
import com.devevents.api.dto.EventResponseDTO;
import com.devevents.api.model.Event;
import com.devevents.api.repository.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Habilita o uso do Mockito nesta classe
class EventServiceTest {

    @Mock // Cria um "dublê" do repositório (não acessa banco de dados real)
    private EventRepository eventRepository;

    @InjectMocks // Injeta o "dublê" acima dentro do nosso serviço real
    private EventService eventService;

    @Test
    @DisplayName("Deve criar um evento com sucesso e retornar o DTO correto")
    void deveCriarEventoComSucesso() {
        // --- 1. ARRANGE (Preparação do cenário) ---

        // Criamos o DTO que simula a entrada do usuário
        EventRequestDTO requestDTO = new EventRequestDTO(
                "Bootcamp de Testes",
                "Aprenda JUnit e Mockito",
                LocalDateTime.now().plusDays(5),
                LocalDateTime.now().plusDays(6),
                "Online"
        );

        Event savedEvent = Event.builder()
                .id(UUID.randomUUID())
                .title(requestDTO.title())
                .description(requestDTO.description())
                .startDate(requestDTO.startDate())
                .endDate(requestDTO.endDate())
                .location(requestDTO.location())
                .active(true)
                .build();

        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);



        EventResponseDTO responseDTO = eventService.createEvent(requestDTO);



        assertNotNull(responseDTO); // O DTO retornado não pode ser nulo
        assertEquals(savedEvent.getId(), responseDTO.id()); // O ID retornado deve ser o mesmo gerado pelo "banco"
        assertEquals("Bootcamp de Testes", responseDTO.title()); // O título deve bater

        verify(eventRepository).save(any(Event.class));
    }
}