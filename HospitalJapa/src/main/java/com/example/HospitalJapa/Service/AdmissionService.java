package com.example.HospitalJapa.Service;

import com.example.HospitalJapa.Model.*;
import com.example.HospitalJapa.DTO.AdmissionRequestDTO;
import com.example.HospitalJapa.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class AdmissionService {

    @Autowired private AdmissionLogRepository admissionLogRepository;
    @Autowired private BedRepository bedRepository;
    @Autowired private PatientRepository patientRepository;

    @Transactional
    public void admitPatient(AdmissionRequestDTO request) {
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Bed bed = bedRepository.findById(request.bedId())
                .orElseThrow(() -> new RuntimeException("Leito não encontrado"));

        if (!"UNOCCUPIED".equals(bed.getStatus())) {
            throw new RuntimeException("Leito já está ocupado");
        }

        bed.setPatient(patient);
        bed.setStatus("OCCUPIED");
        bedRepository.save(bed);

        AdmissionLog log = new AdmissionLog();
        log.setBed(bed);
        log.setPatient(patient);
        log.setDateTime(LocalDateTime.now());
        log.setEventType(EventType.ADMISSION);
        admissionLogRepository.save(log);
    }

    @Transactional
    public void dischargePatient(Long bedId) {
        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new RuntimeException("Leito não encontrado"));

        if (!"OCCUPIED".equals(bed.getStatus())) {
            throw new RuntimeException("Leito não está ocupado");
        }

        Patient patient = bed.getPatient();
        bed.setPatient(null);
        bed.setStatus("IN_PREPARATION");
        bedRepository.save(bed);

        AdmissionLog log = new AdmissionLog();
        log.setBed(bed);
        log.setPatient(patient);
        log.setDateTime(LocalDateTime.now());
        log.setEventType(EventType.DISCHARGE);
        admissionLogRepository.save(log);
    }
}