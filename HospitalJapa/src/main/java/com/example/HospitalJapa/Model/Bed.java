package com.example.HospitalJapa.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "tb_bed")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O número do leito é obrigatório")
    private String bedNumber;

    @NotBlank(message = "O status é obrigatório")
    private String status;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
