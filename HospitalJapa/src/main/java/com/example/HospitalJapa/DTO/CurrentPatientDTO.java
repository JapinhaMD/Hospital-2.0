package com.example.HospitalJapa.DTO;

import java.time.LocalDateTime;

public record CurrentPatientDTO(
        String patientName,
        String specialty,
        LocalDateTime admissionDate,
        long daysAdmitted
) {}