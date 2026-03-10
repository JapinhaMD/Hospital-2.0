package com.example.HospitalJapa.Service;

import com.example.HospitalJapa.DTO.AdmissionLogDTO;
import com.example.HospitalJapa.Model.*;
import com.example.HospitalJapa.DTO.AdmissionRequestDTO;
import com.example.HospitalJapa.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdmissionService {

    @Autowired private AdmissionLogRepository admissionLogRepository;
    @Autowired private BedRepository bedRepository;
    @Autowired private PatientRepository patientRepository;


    @Transactional
    public void admitPatient(AdmissionRequestDTO request) {

        // Query para verificar leitos que estao UNOCCUPIED
        List<Bed> leitosDisponiveis = bedRepository.findAvailableBedsBySpecialty(request.specialty());

        if (leitosDisponiveis.isEmpty()) {
            throw new RuntimeException("Não há nenhum leito disponível para a especialidade: " + request.specialty());
        }


        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Bed bed = bedRepository.findById(request.bedId())
                .orElseThrow(() -> new RuntimeException("Leito não encontrado"));

        if (!bed.getRoom().getWard().getSpecialty().equals(request.specialty())) {
            throw new RuntimeException("Este leito não pertence à ala de " + request.specialty());
        }

        // Valida o status do leito específico
        if (!"UNOCCUPIED".equals(bed.getStatus())) {
            throw new RuntimeException("O leito solicitado já está ocupado ou em preparação");
        }

        if (patient.getIsHospitalized()) {
            throw new RuntimeException("Este paciente já está internado em outro leito.");
        }

        patient.setIsHospitalized(true);
        patientRepository.save(patient);

        bed.setPatient(patient);
        bed.setStatus("OCCUPIED"); // Se nao caiu em nenhuma exception, associa o paciente à cama e muda o statur para OCCUPIED
        bedRepository.save(bed);

        // Registro do Log de ADMISSION
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
            throw new RuntimeException("O leito não está ocupado para realizar a alta");
        }

        Patient patient = bed.getPatient();

        if(patient!= null){
            patient.setIsHospitalized(false);
            patientRepository.save(patient);
        }

        bed.setPatient(null);
        bed.setStatus("IN_PREPARATION");
        bedRepository.save(bed);

        //Relatorio de liberação
        AdmissionLog log = new AdmissionLog();
        log.setBed(bed);
        log.setPatient(patient);
        log.setDateTime(LocalDateTime.now());
        log.setEventType(EventType.DISCHARGE);
        admissionLogRepository.save(log);
    }

    public List<AdmissionLogDTO> listarTodos() {
        return admissionLogRepository.findAll().stream()
                .map(log -> new AdmissionLogDTO(
                        log.getId(),
                        log.getBed().getBedNumber(),
                        log.getBed().getRoom().getRoomCode(),
                        log.getPatient().getName(),
                        log.getDateTime(),
                        log.getEventType()
                ))
                .toList();
    }

}