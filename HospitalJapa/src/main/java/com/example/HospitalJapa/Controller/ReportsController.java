package com.example.HospitalJapa.Controller;

import com.example.HospitalJapa.DTO.*;
import com.example.HospitalJapa.Model.Especialidade;
import com.example.HospitalJapa.Repository.AdmissionLogRepository;
import com.example.HospitalJapa.Repository.BedRepository;
import com.example.HospitalJapa.Repository.PatientRepository;
import com.example.HospitalJapa.Service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reports")
public class ReportsController {

    @Autowired private ReportsService reportsService;



    //Criar requisição para mostrar leitos livres por especialidade
    @GetMapping("/available/specialty")
    public ResponseEntity<List<BedResponseDTO>> getAvailableBySpecialty(@RequestParam Especialidade specialty) {
        List<BedResponseDTO> report = reportsService.listAvailableBySpecialty(specialty);
        return ResponseEntity.ok(report);
    }


    //Criar requisição que retorne pelo id do paciente que está internado:
    @GetMapping("/patient/{patientId}/details")
    public ResponseEntity<PatientHospitalDetailsDTO> getAdmissionDetails(@PathVariable Long patientId) {
        return ResponseEntity.ok(reportsService.getInfosPatient(patientId));
    }


    //Criar requisição para mostrar todos os leitos
    @GetMapping("/beds/all")
    public ResponseEntity<List<BedResponseDTO>> getAll() {
        return ResponseEntity.ok(reportsService.listAll());
    }


    //Criar requisição para mostrar a quantidade de quartos livres, ocupados e total por especialidade
    @GetMapping("/count")
    public ResponseEntity<List<RoomStatusDTO>> getCountByStatus() {
        return ResponseEntity.ok(reportsService.getRoomStatusStats());
    }



    //Criar requisição paginada que retorne o histórico de internamento de um paciente contendo
    @GetMapping("/patient/{patientId}/history")
    public ResponseEntity<Page<PatientHistoryDTO>> getHistory(
            @PathVariable Long patientId,
            @PageableDefault(size = 10, sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(reportsService.getHistoryPatient(patientId, pageable));
    }



    @GetMapping("/active")
    public ResponseEntity<Map<String, List<ActiveAdmissionDTO>>> getActiveBySpecialty() {
        return ResponseEntity.ok(reportsService.getAllCharged());
    }



    @GetMapping("/history/{bedId}")
    public ResponseEntity<List<BedHistoryDTO>> getBedHistory(@PathVariable Long bedId) {
        List<BedHistoryDTO> history = reportsService.getBedHistory(bedId);
        return ResponseEntity.ok(history);
    }




    @GetMapping("/available")
    public ResponseEntity<List<AvailableRoomDTO>> getAvailableRooms() {
        return ResponseEntity.ok(reportsService.getAvailableRooms());
    }


    //Criar requisição que retorne pelo id do paciente que está internado
    @GetMapping("/location/{patientId}")
    public ResponseEntity<PatientLocationDTO> getPatientLocation(@PathVariable Long patientId) {
        PatientLocationDTO location = reportsService.localizePatient(patientId);
        return ResponseEntity.ok(location);
    }
}
