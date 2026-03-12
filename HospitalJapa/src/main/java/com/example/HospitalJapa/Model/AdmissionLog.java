package com.example.HospitalJapa.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_admission_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class AdmissionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bed_id", nullable = false)

    private Bed bed;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)

    private Patient patient;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType eventType;


}

