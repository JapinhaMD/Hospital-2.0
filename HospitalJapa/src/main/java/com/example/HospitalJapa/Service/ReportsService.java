package com.example.HospitalJapa.Service;

import com.example.HospitalJapa.DTO.*;
import com.example.HospitalJapa.Model.AdmissionLog;
import com.example.HospitalJapa.Model.Bed;
import com.example.HospitalJapa.Model.Especialidade;
import com.example.HospitalJapa.Model.Room;
import com.example.HospitalJapa.Repository.AdmissionLogRepository;
import com.example.HospitalJapa.Repository.BedRepository;
import com.example.HospitalJapa.Repository.PatientRepository;
import com.example.HospitalJapa.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportsService {

    @Autowired private AdmissionLogRepository admissionLogRepository;
    @Autowired private BedRepository bedRepository;
    @Autowired private PatientRepository patientRepository;
    @Autowired private RoomRepository roomRepository;


    //Criar requisição que retorne pelo id do paciente que está internado
    public PatientHospitalDetailsDTO getInfosPatient(Long patientId) {
        AdmissionLog log = admissionLogRepository.findInfosPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado ou não possui internação ativa."));

        return new PatientHospitalDetailsDTO(
                log.getBed().getRoom().getWard().getHospital().getName(),
                log.getBed().getRoom().getWard().getSpecialty().toString(),
                log.getBed().getRoom().getRoomCode(),
                log.getPatient().getName(),
                log.getDateTime()
        );
    }


    //Criar requisição paginada que retorne o histórico de internamento de um paciente contendo
    public Page<PatientHistoryDTO> getHistoryPatient(Long patientId, Pageable pageable) {
        Page<AdmissionLog> logs = admissionLogRepository.findHistoryByPatientId(patientId, pageable);

        return logs.map(log -> new PatientHistoryDTO(
                log.getPatient().getName(),
                log.getBed().getRoom().getWard().getSpecialty().toString(),
                log.getDateTime()
        ));
    }


    public Map<String, List<ActiveAdmissionDTO>> getAllCharged() {
        List<AdmissionLog> activeLogs = admissionLogRepository.findAllActiveAdmissions();

        return activeLogs.stream()
                .map(log -> {
                    long days = ChronoUnit.DAYS.between(log.getDateTime(), LocalDateTime.now());
                    return new ActiveAdmissionDTO(
                            log.getPatient().getName(),
                            log.getBed().getRoom().getWard().getSpecialty().toString(),
                            log.getDateTime(),
                            days
                    );
                })
                .collect(Collectors.groupingBy(ActiveAdmissionDTO::wardSpecialty));
    }


    public List<BedHistoryDTO> getBedHistory(Long bedId) {
        List<AdmissionLog> logs = admissionLogRepository.findHistoryByBedId(bedId);

        return logs.stream()
                .map(log -> new BedHistoryDTO(
                        log.getBed().getBedNumber(),
                        log.getPatient().getName(),
                        log.getDateTime(),
                        log.getEventType().toString()
                ))
                .toList();
    }


    public List<BedResponseDTO> listAvailableBySpecialty(Especialidade specialty) {
        List<Bed> beds = bedRepository.findAvailableBedsBySpecialty(specialty);

        return beds.stream()
                .map(b -> new BedResponseDTO(
                        b.getId(),
                        b.getRoom().getWard().getHospital().getId(),
                        b.getBedNumber() != null ? b.getBedNumber(): "S/N",
                        b.getStatus(),
                        b.getRoom().getRoomCode()
                ))
                .toList();
    }



    public List<RoomStatusDTO> getRoomStatusStats() {
        List<Room> allRooms = roomRepository.findAll();

        return allRooms.stream()
                .collect(Collectors.groupingBy(r -> r.getWard().getSpecialty().toString()))
                .entrySet().stream()
                .map(entry -> {
                    String specialty = entry.getKey();
                    List<Room> roomsBySpecialty = entry.getValue();

                    long total = roomsBySpecialty.size();
                    long occupied = roomsBySpecialty.stream()
                            .filter(room -> room.getBeds().stream()
                                    .anyMatch(bed -> "OCCUPIED".equals(bed.getStatus())))
                            .count();

                    long free = total - occupied;
                    return new RoomStatusDTO(specialty, total, free, occupied);
                })
                .toList();
    }


    public List<AvailableRoomDTO> getAvailableRooms() {
        List<Room> rooms = roomRepository.findRoomsWithAvailableBeds();

        return rooms.stream()
                .map(r -> new AvailableRoomDTO(
                        r.getWard().getSpecialty().toString(),
                        r.getRoomCode()
                ))
                .toList();
    }


    public PatientLocationDTO localizePatient(Long patientId) {
        AdmissionLog log = admissionLogRepository.findActiveAdmissionByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente não localizado ou já recebeu alta."));

        return new PatientLocationDTO(
                log.getPatient().getId(),
                log.getPatient().getName(),
                log.getBed().getRoom().getWard().getHospital().getName(),
                log.getBed().getRoom().getWard().getSpecialty().toString(),
                log.getBed().getRoom().getRoomCode(),
                log.getBed().getBedNumber()
        );
    }


    public List<BedResponseDTO> listAll() {
        return bedRepository.findAll().stream()
                .map(b -> new BedResponseDTO(
                        b.getId(),
                        b.getRoom().getWard().getHospital().getId(),
                        b.getBedNumber() != null ? b.getBedNumber(): "S/N",
                        b.getStatus(),
                        b.getRoom().getRoomCode()
                ))
                .toList();
    }

}
