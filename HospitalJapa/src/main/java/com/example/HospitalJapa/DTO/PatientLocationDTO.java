package com.example.HospitalJapa.DTO;

public record PatientLocationDTO(
        Long patientId,
        String patientName,
        String hospitalName,
        String wardSpecialty,
        String roomCode,
        String bedNumber

) {}