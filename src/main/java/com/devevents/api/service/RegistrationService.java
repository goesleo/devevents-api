package com.devevents.api.service;

import com.devevents.api.dto.RegistrationRequestDTO;
import com.devevents.api.dto.RegistrationResponseDTO;
import com.devevents.api.model.Event;
import com.devevents.api.model.Participant;
import com.devevents.api.model.Registration;
import com.devevents.api.repository.EventRepository;
import com.devevents.api.repository.ParticipantRepository;
import com.devevents.api.repository.RegistrationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    @Transactional
    public RegistrationResponseDTO register(RegistrationRequestDTO dto) {

        //  1: O evento existe e está ativo?
        Event event = eventRepository.findById(dto.eventId()).orElseThrow(() -> new IllegalArgumentException("Evento não encontrado."));

        if (!event.isActive()) {
            throw new IllegalArgumentException("Este evento não está mais ativo para inscrições.");
        }

        // 2: Busca o participante pelo e-mail. Se não existir, cria um novo na hora!
        Participant participant = participantRepository.findByEmail(dto.participantEmail()).orElseGet(() -> {
            Participant newParticipant = Participant.builder().name(dto.participantName()).email(dto.participantEmail()).active(true).build();
            return participantRepository.save(newParticipant);
        });

        //  3: Prevenção de Inscrição Duplicada
        boolean alreadyRegistered = registrationRepository.existsByEventIdAndParticipantId(event.getId(), participant.getId());

        if (alreadyRegistered) {
            throw new IllegalArgumentException("Participante já está inscrito neste evento.");
        }

        // Se passou por todas as regras, cria a inscrição
        Registration registration = Registration.builder().event(event).participant(participant)
                // A data de inscrição é gerada automaticamente pelo @CreationTimestamp na Entidade
                .build();

        Registration savedRegistration = registrationRepository.save(registration);

        return new RegistrationResponseDTO(savedRegistration);
    }

    public List<RegistrationResponseDTO> getEventRegistrations(UUID eventId) {
        // Verifica se o evento existe antes de buscar (boa prática)
        if (!eventRepository.existsById(eventId)) {
            throw new IllegalArgumentException("Evento não encontrado.");
        }

        // Busca a lista de inscrições, transforma cada uma em um DTO e devolve como uma Lista
        return registrationRepository.findAllByEventId(eventId)
                .stream()
                .map(RegistrationResponseDTO::new) // Nosso DTO já sabe como se construir a partir da Entidade
                .toList();
    }
}