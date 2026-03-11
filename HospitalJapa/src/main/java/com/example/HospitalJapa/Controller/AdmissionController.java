package com.example.HospitalJapa.Controller;

import com.example.HospitalJapa.DTO.*;
import com.example.HospitalJapa.Service.AdmissionService;
import com.example.HospitalJapa.Repository.AdmissionLogRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admissions")
public class AdmissionController {

    @Autowired
    private AdmissionService admissionService;

    @Autowired
    private AdmissionLogRepository admissionLogRepository;


    @GetMapping
    public ResponseEntity<List<AdmissionLogDTO>> listarTodos() {
        return ResponseEntity.ok(admissionService.listAll());
    }


    @PostMapping("/admit")
    public ResponseEntity<String> admitPatient(@Valid @RequestBody AdmissionRequestDTO request) {
        admissionService.admitPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Paciente internado com sucesso");
    }


    @PostMapping("/discharge/{bedId}")
    public ResponseEntity<String> dischargePatient(@PathVariable Long bedId) {
        admissionService.dischargePatient(bedId);
        return ResponseEntity.ok("Alta realizada com sucesso. O leito agora está em preparação.");
    }


    @GetMapping("/patient/{patientId}/details")
    public ResponseEntity<PatientHospitalDetailsDTO> getAdmissionDetails(@PathVariable Long patientId) {
        return ResponseEntity.ok(admissionService.getInfosPatient(patientId));
    }


    @GetMapping("/patient/{patientId}/history")
    public ResponseEntity<Page<PatientHistoryDTO>> getHistory(
            @PathVariable Long patientId,
            @PageableDefault(size = 10, sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(admissionService.getHistoryPatient(patientId, pageable));
    }


    @GetMapping("/active")
    public ResponseEntity<Map<String, List<ActiveAdmissionDTO>>> getActiveBySpecialty() {
        return ResponseEntity.ok(admissionService.getAllCharged());
    }

    @GetMapping("/history/{bedId}")
    public ResponseEntity<List<BedHistoryDTO>> getBedHistory(@PathVariable Long bedId) {
        List<BedHistoryDTO> history = admissionService.getBedHistory(bedId);
        return ResponseEntity.ok(history);
    }
}