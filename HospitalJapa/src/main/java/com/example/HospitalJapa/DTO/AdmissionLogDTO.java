package com.example.HospitalJapa.DTO;

import com.example.HospitalJapa.Model.EventType;
import java.time.LocalDateTime;

public record AdmissionLogDTO(
        Long id,
        String bedNumber,
        String roomCode,
        String patientName,
        LocalDateTime dateTime,
        EventType eventType
) {}