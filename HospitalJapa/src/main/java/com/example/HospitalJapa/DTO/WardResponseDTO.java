package com.example.HospitalJapa.DTO;

import java.util.List;

public record WardResponseDTO(
        Long id,
        String specialty,
        Long hospitalId,
        Integer quantidadeQuartos,
        Integer quantidadeLeitosPorQuarto,
        List<RoomResponseDTO> rooms
) {}