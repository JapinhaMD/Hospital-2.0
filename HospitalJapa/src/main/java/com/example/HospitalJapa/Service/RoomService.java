package com.example.HospitalJapa.Service;

import com.example.HospitalJapa.Model.Room;
import com.example.HospitalJapa.Model.Ward;
import com.example.HospitalJapa.Repository.RoomRepository;
import com.example.HospitalJapa.Repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    @Autowired private RoomRepository roomRepository;
    @Autowired private WardRepository wardRepository;
    @Autowired private BedService bedService;

    public List<Room> getRoomsByWardId(Long wardId) {
        return roomRepository.findByWardId(wardId);
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado com id: " + id));
    }


    @Transactional
    public Room createRoom(Long wardId, Room room, Integer quantidadeLeitos) {
        Ward ward = wardRepository.findById(wardId)
                .orElseThrow(() -> new RuntimeException("Ala não encontrada com id: " + wardId));

        List<Room> existingRooms = roomRepository.findByWardId(wardId);
        int roomNumber = existingRooms.size() + 1;

        room.setWard(ward);
        room.setRoomCode(generateRoomCode(ward.getSpecialty().toString(), roomNumber));
        room.setStatus("ACTIVE");
        room.setBeds(new ArrayList<>());

        Room roomSalvo = roomRepository.save(room);

        if (quantidadeLeitos != null && quantidadeLeitos > 0) {
            bedService.createBeds(roomSalvo, quantidadeLeitos);
        }

        return roomSalvo;
    }


    public void createRoomsBulk(Ward ward, String specialty, int quantidadeQuartos, Integer quantidadeLeitosPorQuarto) {
        for (int i = 1; i <= quantidadeQuartos; i++) {
            Room room = new Room();
            room.setWard(ward);
            room.setRoomCode(generateRoomCode(specialty, i));
            room.setStatus("ACTIVE");
            room.setBeds(new ArrayList<>());

            if (quantidadeLeitosPorQuarto != null && quantidadeLeitosPorQuarto > 0) {
                bedService.createBeds(room, quantidadeLeitosPorQuarto);
            }

            ward.getRooms().add(room);
        }
    }


    public String generateRoomCode(String specialty, int roomNumber) {
        String[] words = specialty.split(" ");
        StringBuilder code = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                code.append(word.substring(0, 1).toUpperCase());
            }
        }

        if (code.length() < 2) {
            code = new StringBuilder(specialty.substring(0, Math.min(3, specialty.length())).toUpperCase());
        }

        code.append("-").append(roomNumber);
        return code.toString();
    }

    @Transactional
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}