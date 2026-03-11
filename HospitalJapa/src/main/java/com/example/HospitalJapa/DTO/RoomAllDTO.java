package com.example.HospitalJapa.DTO;

import java.util.List;

public record RoomAllDTO(
        Long id,
        String roomCode,
        String status,
        String wardSpecialty,
        Long hospitalId,
        String hospitalName,
        List<BedResponseDTO> beds
) {}