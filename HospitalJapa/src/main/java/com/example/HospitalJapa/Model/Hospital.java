package com.example.HospitalJapa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tb_hospital")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter


public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @NotBlank(message = "O telefone é obrigatório")
    private String phone;

    @NotBlank(message = "O cnpj é obrigatório")
    private String cnpj;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ward> wards = new ArrayList<>();
}
