package com.example.HospitalJapa.Service;

import com.example.HospitalJapa.DTO.*;
import com.example.HospitalJapa.Model.*;
import com.example.HospitalJapa.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomStatusService {

    @Autowired private BedRepository bedRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private AdmissionLogRepository admissionLogRepository;


    public List<CurrentPatientDTO> listCurrentPatients() {
        return bedRepository.findAll().stream()
                .filter(bed -> bed.getPatient() != null && "OCCUPIED".equals(bed.getStatus()))
                .map(bed -> {

                    AdmissionLog lastAdmission = admissionLogRepository
                            .findTopByBedIdAndPatientIdAndEventTypeOrderByDateTimeDesc(
                                    bed.getId(), bed.getPatient().getId(), EventType.ADMISSION);

                    long daysAdmitted = 0;
                    LocalDateTime admissionDate = null;

                    if (lastAdmission != null) {
                        admissionDate = lastAdmission.getDateTime();
                        daysAdmitted = Duration.between(admissionDate, LocalDateTime.now()).toDays();
                    }

                    return new CurrentPatientDTO(
                            bed.getPatient().getName(),
                            bed.getRoom().getWard().getSpecialty().toString(),
                            admissionDate,
                            daysAdmitted
                    );
                })
                // Ordenação alfabética nome
                .sorted(Comparator.comparing(CurrentPatientDTO::patientName))
                .collect(Collectors.toList());
    }


    public PatientHospitalDetailsDTO getPatientDetails(Long patientId) {
        // Ver leito do paciente
        Bed bed = bedRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado ou não está internado."));

        // Buscar data entrada
        AdmissionLog log = admissionLogRepository
                .findTopByBedIdAndPatientIdAndEventTypeOrderByDateTimeDesc(
                        bed.getId(), patientId, EventType.ADMISSION);

        return new PatientHospitalDetailsDTO(
                bed.getRoom().getWard().getHospital().getName(),
                bed.getRoom().getWard().getSpecialty().toString(),
                bed.getRoom().getRoomCode(),
                bed.getPatient().getName(),
                log != null ? log.getDateTime() : null
        );
    }


    public List<RoomStatusDTO> getRoomStatusReport() {
        return roomRepository.countRoomsBySpecialtyAndStatus();
    }


    public List<BedHistoryDTO> getBedHistory(Long bedId) {
        List<AdmissionLog> logs = admissionLogRepository.findByBedIdOrderByDateTimeDesc(bedId);

        return logs.stream().map(log -> new BedHistoryDTO(
                log.getBed().getBedNumber(),
                log.getPatient().getName(),
                log.getDateTime(),
                log.getEventType().toString()
        )).collect(Collectors.toList());
    }
}