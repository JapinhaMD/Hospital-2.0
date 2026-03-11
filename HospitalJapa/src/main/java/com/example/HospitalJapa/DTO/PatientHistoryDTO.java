package com.example.HospitalJapa.DTO;

import java.time.LocalDateTime;

public record PatientHistoryDTO(
        String patientName,
        String wardSpecialty,
        LocalDateTime dateTime
) {}