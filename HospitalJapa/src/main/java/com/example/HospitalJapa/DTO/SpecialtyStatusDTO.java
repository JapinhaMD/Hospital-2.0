package com.example.HospitalJapa.DTO;

public record SpecialtyStatusDTO(
        String specialty,
        Long totalRooms,
        Long freeRooms,
        Long occupiedRooms
) {}

