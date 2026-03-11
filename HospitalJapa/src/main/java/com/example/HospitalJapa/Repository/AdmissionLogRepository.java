package com.example.HospitalJapa.Repository;

import com.example.HospitalJapa.Model.AdmissionLog;
import com.example.HospitalJapa.Model.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdmissionLogRepository extends JpaRepository<AdmissionLog, Long> {

    Optional<AdmissionLog> findById (Long id);

    @Query("SELECT a FROM AdmissionLog a " + "WHERE a.patient.id = :patientId " + "AND a.patient.isHospitalized = true")
    Optional<AdmissionLog> findActiveAdmissionByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT a FROM AdmissionLog a " + "WHERE a.patient.id = :patientId " + "AND a.patient.isHospitalized = true ")
    Optional<AdmissionLog> findInfosPatientId(@Param("patientId") Long patientId);

    @Query("SELECT a FROM AdmissionLog a " + "WHERE a.patient.id = :patientId " + "ORDER BY a.dateTime DESC")
    Page<AdmissionLog> findHistoryByPatientId(@Param("patientId") Long patientId, Pageable pageable);

    @Query("SELECT a FROM AdmissionLog a " + "WHERE a.patient.isHospitalized = true " + "ORDER BY a.patient.name ASC")
    List<AdmissionLog> findAllActiveAdmissions();

    @Query("SELECT a FROM AdmissionLog a " + "WHERE a.bed.id = :bedId " + "ORDER BY a.dateTime ASC")
    List<AdmissionLog> findHistoryByBedId(@Param("bedId") Long bedId);
}