package com.devevents.api.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O titulo é obrigatorio")
    @Column(nullable = false, length = 150)
    private String title;

    @NotBlank(message = "A descrição é obrigatoria")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "A data de inicio é obrigatoria")
    @FutureOrPresent(message = "A data do evento nçao pode estar no passado")
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @NotNull(message = "A data de término é obrigatória")
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(length = 200)
    private String location;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;


}
