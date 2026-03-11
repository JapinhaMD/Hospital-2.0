package com.example.HospitalJapa.Controller;

import com.example.HospitalJapa.DTO.AvailableRoomDTO;
import com.example.HospitalJapa.DTO.RoomStatusDTO;
import com.example.HospitalJapa.DTO.SpecialtyStatusDTO;
import com.example.HospitalJapa.Model.Room;
import com.example.HospitalJapa.Service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired private RoomService roomService;


    @GetMapping("/{id}")
    public ResponseEntity<Room> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }


    @GetMapping("/ward/{wardId}")
    public ResponseEntity<List<Room>> listarQuartosDaAla(@PathVariable Long wardId) {
        return ResponseEntity.ok(roomService.getRoomsByWardId(wardId));
    }


    @PostMapping("/ward/{wardId}")
    public ResponseEntity<Room> criarQuarto(
            @PathVariable Long wardId,
            @RequestParam(required = false) Integer qtdLeitos,
            @Valid @RequestBody Room room) {

        Room roomCriado = roomService.createRoom(wardId, room, qtdLeitos);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomCriado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/status")
    public ResponseEntity<List<SpecialtyStatusDTO>> getStats() {
        return ResponseEntity.ok(roomService.getRoomsStatsBySpecialty());
    }


    @GetMapping("/available")
    public ResponseEntity<List<AvailableRoomDTO>> getAvailableRooms() {
        return ResponseEntity.ok(roomService.getAvailableRooms());
    }

    @GetMapping("/count")
    public ResponseEntity<List<RoomStatusDTO>> getCountByStatus() {
        return ResponseEntity.ok(roomService.getRoomStatusStats());
    }

}