package com.example.HospitalJapa.DTO;

public record RoomStatusDTO(
        String specialty,
        long totalRooms,
        long freeRooms,
        long occupiedRooms
) {}