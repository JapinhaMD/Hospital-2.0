package com.example.HospitalJapa.DTO;

import com.example.HospitalJapa.Model.Especialidade;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record HospitalDTO(
        @NotBlank(message = "O nome é obrigatório") String name,
        @NotBlank(message = "O telefone é obrigatório") String phone,
        @NotBlank(message = "O cnpj é obrigatório") String cnpj,

        List<WardRequestDTO> wards
) {
    public record WardRequestDTO(
            @NotNull(message = "A especialidade é obrigatória") Especialidade specialty,

            @Min(value = 1, message = "Se informado, deve ter pelo menos 1 quarto")
            Integer quantidadeQuartos,

            @Min(value = 1, message = "Se informado, deve ter pelo menos 1 leito por quarto")
            Integer quantidadeLeitosPorQuarto
    ) {}
}