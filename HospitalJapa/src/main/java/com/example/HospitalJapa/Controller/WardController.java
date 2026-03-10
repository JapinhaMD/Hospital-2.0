package com.example.HospitalJapa.Controller;

import com.example.HospitalJapa.DTO.WardDTO;
import com.example.HospitalJapa.Model.Ward;
import com.example.HospitalJapa.Service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Ward>> listarPorHospital(@PathVariable Long hospitalId) {
        return ResponseEntity.ok(wardService.getWardsByHospitalId(hospitalId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WardDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(wardService.getWardDTOById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        wardService.deleteWard(id);
        return ResponseEntity.noContent().build();
    }
}