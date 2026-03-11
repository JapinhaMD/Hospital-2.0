package com.example.HospitalJapa.DTO;

import com.example.HospitalJapa.Model.Especialidade;

public record RoomStatusDTO(
        String specialty,
        long totalRooms,
        long freeRooms,
        long occupiedRooms
) {}