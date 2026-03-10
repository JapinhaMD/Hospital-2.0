package com.example.HospitalJapa.DTO;

import com.example.HospitalJapa.Model.Especialidade;
import jakarta.validation.constraints.NotNull;

public record AdmissionRequestDTO(
        @NotNull(message = "O ID do paciente é obrigatório") Long patientId,
        @NotNull(message = "O ID do leito é obrigatório") Long bedId,
        @NotNull(message = "A especialidade é obrigatória") Especialidade specialty
) {}

