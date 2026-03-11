package com.example.HospitalJapa.DTO;

public record BedResponseDTO(
        Long id,
        Long idHospital,
        String bedNumber,
        String status,
        String wardSpecialty
) {}