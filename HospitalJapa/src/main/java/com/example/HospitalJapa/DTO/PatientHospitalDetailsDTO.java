package com.example.HospitalJapa.DTO;

import java.time.LocalDateTime;

public record PatientHospitalDetailsDTO(
        String hospitalName,
        String specialty,
        String roomCode,
        String patientName,
        LocalDateTime admissionDate
) {}