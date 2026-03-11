package com.example.HospitalJapa.DTO;

import java.util.List;

public record HospitalResponseDTO(
        Long id,
        String name,
        String phone,
        String cnpj,
        List<WardResponseDTO> wards
) {}
