package com.example.HospitalJapa.Controller;

import com.example.HospitalJapa.DTO.BedDTO;
import com.example.HospitalJapa.Model.Bed;
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
    public ResponseEntity<BedDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(bedService.getBedDTOById(id));
    }


    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<BedDTO>> listarPorQuarto(@PathVariable Long roomId) {
        return ResponseEntity.ok(bedService.getBedDTOsByRoomId(roomId));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLeito(@PathVariable Long id) {
        bedService.deleteBed(id);
        return ResponseEntity.noContent().build();
    }

}