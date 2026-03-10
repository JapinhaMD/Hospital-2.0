package com.example.HospitalJapa.Repository;

import com.example.HospitalJapa.Model.AdmissionLog;
import com.example.HospitalJapa.Model.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AdmissionLogRepository extends JpaRepository<AdmissionLog, Long> {
    // Histórico paginado do paciente
    Page<AdmissionLog> findByPatientIdOrderByDateTimeDesc(Long patientId, Pageable pageable);

    // Histórico de um leito específico
    List<AdmissionLog> findByBedIdOrderByDateTimeDesc(Long bedId);

    AdmissionLog findTopByBedIdAndPatientIdAndEventTypeOrderByDateTimeDesc(
            Long bedId, Long patientId, EventType eventType);

    Optional<AdmissionLog> findById (Long id);
}