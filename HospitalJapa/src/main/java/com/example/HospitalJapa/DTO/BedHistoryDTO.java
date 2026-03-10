package com.example.HospitalJapa.DTO;

import java.time.LocalDateTime;

public record BedHistoryDTO(
        @jakarta.validation.constraints.NotBlank(message = "O número do leito é obrigatório") String bedNumber,
        String patientName,
        LocalDateTime dateTime,
        String eventType
) {}