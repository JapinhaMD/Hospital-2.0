package com.example.HospitalJapa.DTO;

import java.util.List;

public record RoomResponseDTO(
        Long id,
        String roomCode,
        String status,
        List<BedResponseDTO> beds // Adiciona esta linha
) {}