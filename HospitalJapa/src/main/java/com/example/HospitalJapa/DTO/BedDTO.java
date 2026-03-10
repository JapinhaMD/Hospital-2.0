package com.example.HospitalJapa.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BedDTO {
    private Long id;
    private String bedNumber;
    private String status;
    private Long roomId;
    private Long patientId;
}
