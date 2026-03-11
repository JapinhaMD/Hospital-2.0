package com.example.HospitalJapa.DTO;

import java.time.LocalDateTime;

public record BedHistoryDTO(
        String bedCode,
        String patientName,
        LocalDateTime dateTime,
        String eventType
) {}