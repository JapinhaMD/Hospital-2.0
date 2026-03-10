package com.example.HospitalJapa.Controller;

import com.example.HospitalJapa.DTO.*;
import com.example.HospitalJapa.Model.*;
import com.example.HospitalJapa.Service.AdmissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admissions")
public class AdmissionController {

    @Autowired
    private AdmissionService admissionService;


    @PostMapping("/admit")
    public ResponseEntity<String> internarPaciente(@Valid @RequestBody AdmissionRequestDTO request) {
        admissionService.admitPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Paciente internado com sucesso");
    }

    @PostMapping("/discharge/{bedId}")
    public ResponseEntity<String> darAltaPaciente(@PathVariable Long bedId) {
        admissionService.dischargePatient(bedId);
        return ResponseEntity.ok("Paciente dado alta com sucesso");
    }


}
