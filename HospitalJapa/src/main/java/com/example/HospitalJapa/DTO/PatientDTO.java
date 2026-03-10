package com.example.HospitalJapa.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientDTO {
    private Long id;
    private String name;
    private String cpf;
    private String phone;
}
