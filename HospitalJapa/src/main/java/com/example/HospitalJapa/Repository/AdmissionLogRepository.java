package com.example.HospitalJapa.Repository;

import com.example.HospitalJapa.Model.AdmissionLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AdmissionLogRepository extends JpaRepository<AdmissionLog, Long> {

    @Query("SELECT al FROM AdmissionLog al WHERE al.patient.id = :patientId ORDER BY al.dateTime DESC")
    Page<AdmissionLog> findByPatientId(@Param("patientId") Long patientId, Pageable pageable);

    @Query("SELECT al FROM AdmissionLog al WHERE al.bed.id = :bedId ORDER BY al.dateTime DESC")
    List<AdmissionLog> findByBedId(@Param("bedId") Long bedId);

    @Query("SELECT al FROM AdmissionLog al WHERE al.patient.id = :patientId AND al.eventType = 'ADMISSION' ORDER BY al.dateTime DESC LIMIT 1")
    AdmissionLog findLastAdmissionByPatientId(@Param("patientId") Long patientId);
}
