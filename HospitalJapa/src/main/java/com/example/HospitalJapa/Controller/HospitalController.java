package com.example.HospitalJapa.Controller;

import com.example.HospitalJapa.DTO.HospitalDTO;
import com.example.HospitalJapa.DTO.HospitalResponseDTO;
import com.example.HospitalJapa.Model.Hospital;
import com.example.HospitalJapa.Service.HospitalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;
    @GetMapping
    public ResponseEntity<List<HospitalResponseDTO>> listarTodos() {
        List<HospitalResponseDTO> response = hospitalService.listarTodos()
                .stream()
                .map(hospitalService::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<HospitalResponseDTO> buscarPorId(@PathVariable Long id) {
        Hospital hospital = hospitalService.buscarPorId(id);
        return ResponseEntity.ok(hospitalService.convertToResponseDTO(hospital));
    }


    @PostMapping
    public ResponseEntity<HospitalResponseDTO> createHospital(@Valid @RequestBody HospitalDTO dto) {
        HospitalResponseDTO response = hospitalService.createHospital(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<HospitalResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody Hospital hospitalAtualizado) {
        Hospital hospital = hospitalService.updateHospital(id, hospitalAtualizado);
        return ResponseEntity.ok(hospitalService.convertToResponseDTO(hospital));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        hospitalService.deleteHospital(id);
        return ResponseEntity.noContent().build();
    }
}