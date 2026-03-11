package com.example.HospitalJapa.Controller;

import com.example.HospitalJapa.DTO.BedDTO;
import com.example.HospitalJapa.DTO.BedResponseDTO;
import com.example.HospitalJapa.Model.Especialidade;
import com.example.HospitalJapa.Service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/beds")
public class BedController {

    @Autowired
    private BedService bedService;


    @GetMapping("/{id}")
    public ResponseEntity<BedDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bedService.getBedDTOById(id));
    }


    @GetMapping
    public ResponseEntity<List<BedResponseDTO>> getAll() {
        return ResponseEntity.ok(bedService.listAll());
    }


    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<BedDTO>> listByRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(bedService.getBedDTOsByRoomId(roomId));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBed(@PathVariable Long id) {
        bedService.deleteBed(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/available")
    public ResponseEntity<List<BedResponseDTO>> getAvailableBySpecialty(@RequestParam Especialidade specialty) {
        List<BedResponseDTO> report = bedService.listAvailableBySpecialty(specialty);
        return ResponseEntity.ok(report);
    }

}