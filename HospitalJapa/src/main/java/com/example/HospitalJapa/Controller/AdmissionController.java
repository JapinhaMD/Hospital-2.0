package com.example.HospitalJapa.Controller;

import com.example.HospitalJapa.DTO.*;
import com.example.HospitalJapa.Model.*;
import com.example.HospitalJapa.Service.AdmissionService;
import com.example.HospitalJapa.Repository.AdmissionLogRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(admissionService.listarTodos());
    }

    @PostMapping("/admit")
    public ResponseEntity<String> internarPaciente(@Valid @RequestBody AdmissionRequestDTO request) {
        admissionService.admitPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Paciente internado com sucesso");
    }

    @PostMapping("/discharge/{bedId}")
    public ResponseEntity<String> darAltaPaciente(@PathVariable Long bedId) {
        admissionService.dischargePatient(bedId);
        return ResponseEntity.ok("Alta realizada com sucesso. O leito agora está em preparação.");
    }


    @GetMapping("/patient/{patientId}/history")
    public ResponseEntity<Page<AdmissionLog>> getPatientHistory(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(admissionLogRepository.findByPatientIdOrderByDateTimeDesc(patientId, pageable));
    }
}