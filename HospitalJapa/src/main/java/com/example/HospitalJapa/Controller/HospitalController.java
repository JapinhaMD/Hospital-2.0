package com.example.HospitalJapa.Controller;

import com.example.HospitalJapa.DTO.HospitalDTO;
import com.example.HospitalJapa.Model.*;
import com.example.HospitalJapa.Service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired private HospitalService hospitalService;
    @Autowired private RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Hospital>> listarTodos() {
        return ResponseEntity.ok(hospitalService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospital> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(hospitalService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Hospital> criarHospitalCompleto(@Valid @RequestBody HospitalDTO dto) {
        Hospital hospitalCriado = hospitalService.createHospitalCompleto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospital> atualizar(@PathVariable Long id, @Valid @RequestBody Hospital hospitalAtualizado) {
        Hospital hospital = hospitalService.updateHospital(id, hospitalAtualizado);
        return ResponseEntity.ok(hospital);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        hospitalService.deleteHospital(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/wards/{wardId}/rooms")
    public ResponseEntity<List<Room>> listarQuartosDAla(@PathVariable Long wardId) {
        return ResponseEntity.ok(roomService.getRoomsByWardId(wardId));
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<Room> buscarQuarto(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    @PostMapping("/wards/{wardId}/rooms")
    public ResponseEntity<Room> criarQuarto(
            @PathVariable Long wardId,
            @RequestParam(required = false) Integer qtdLeitos, // Opcional
            @Valid @RequestBody Room room) {


        Room roomCriado = roomService.createRoom(wardId, room, qtdLeitos);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomCriado);
    }
}