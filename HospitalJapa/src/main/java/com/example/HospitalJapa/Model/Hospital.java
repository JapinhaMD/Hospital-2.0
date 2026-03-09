package com.example.HospitalJapa.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


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
    private String nome;

    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "O cnpj é obrigatório")
    private String cnpj;
}
