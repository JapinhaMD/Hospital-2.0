package com.example.HospitalJapa.Controller;

import com.example.HospitalJapa.DTO.HospitalDTO;
import com.example.HospitalJapa.DTO.WardDTO;
import com.example.HospitalJapa.Model.Ward;
import com.example.HospitalJapa.Service.WardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/wards")
public class WardController {

    @Autowired
    private WardService wardService;



    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<Ward>> listByHospital(@PathVariable Long hospitalId) {
        return ResponseEntity.ok(wardService.getWardsByHospitalId(hospitalId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WardDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(wardService.getWardDTOById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        wardService.deleteWard(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{hospitalId}/add")
    public ResponseEntity<String> addWardsToHospital(@PathVariable Long hospitalId, @RequestBody @Valid List<HospitalDTO.WardRequestDTO> wardDtos) {
        wardService.addWardsToExistingHospital(hospitalId, wardDtos);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Novas alas e quartos adicionados com sucesso ao hospital!");
    }
}