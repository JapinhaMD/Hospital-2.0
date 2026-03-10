package com.example.HospitalJapa.Controller;

import com.example.HospitalJapa.DTO.PatientDTO;
import com.example.HospitalJapa.Model.Patient;
import com.example.HospitalJapa.Service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientDTO>> listarTodos() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientDTOById(id));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Patient> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(patientService.getPatientByCpf(cpf));
    }

    @PostMapping
    public ResponseEntity<Patient> criar(@Valid @RequestBody PatientDTO request) {
        Patient patient = patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> atualizar(@PathVariable Long id, @Valid @RequestBody PatientDTO request) {
        Patient patient = patientService.updatePatient(id, request);
        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
