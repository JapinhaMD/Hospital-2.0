package com.example.HospitalJapa.DTO;

import java.time.LocalDateTime;

public record ActiveAdmissionDTO(
        String patientName,
        String wardSpecialty,
        LocalDateTime chargeDate,
        long daysHospitalized
) {}