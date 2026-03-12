package com.example.HospitalJapa.Controller;

import com.example.HospitalJapa.DTO.*;
import com.example.HospitalJapa.Service.AdmissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admissions")
public class AdmissionController {

    @Autowired private AdmissionService admissionService;



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


}