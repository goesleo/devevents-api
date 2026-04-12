package com.devevents.api.service;

import com.devevents.api.dto.EventRequestDTO;
import com.devevents.api.dto.EventResponseDTO;
import com.devevents.api.model.Event;
import com.devevents.api.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // O Lombok cria o construtor para injetar o repositório
public class EventService {
    private final EventRepository eventRepository;

    @Transactional
    public EventResponseDTO createEvent(EventRequestDTO dto){
        Event newEvent = Event.builder()
                .title(dto.title())
                .description(dto.description())
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .location(dto.location())
                .active(true)
                .build();
        // 2. Salva no banco de dados
        Event savedEvent = eventRepository.save(newEvent);

        // 3. Converte a Entidade de volta para DTO (resposta) e retorna
        return new EventResponseDTO(savedEvent);

    }

    public List<EventResponseDTO> getActiveEvents() {
        return eventRepository.findAllByActiveTrue()
                .stream()
                .map(EventResponseDTO::new) // Usa o nosso construtor inteligente do Record
                .toList();
        }

}
