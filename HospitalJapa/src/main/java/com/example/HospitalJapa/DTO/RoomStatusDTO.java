package com.example.HospitalJapa.DTO;

import com.example.HospitalJapa.Model.Especialidade;

public record RoomStatusDTO(
        Especialidade specialty,
        String status,
        Long count
) {}