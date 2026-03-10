package com.example.HospitalJapa.Service;

import com.example.HospitalJapa.Model.Room;
import com.example.HospitalJapa.Model.Ward;
import com.example.HospitalJapa.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    @Autowired private RoomRepository roomRepository;
    @Autowired private BedService bedService;

    public List<Room> getRoomsByWardId(Long wardId) {
        return roomRepository.findByWardId(wardId);
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado com id: " + id));
    }

    public void createRooms(Ward ward, String specialty, int quantidadeQuartos, Integer quantidadeLeitosPorQuarto) {
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
        String code = (specialty.length() >= 3)
                ? specialty.substring(0, 3).toUpperCase()
                : specialty.toUpperCase();
        return code + "-" + roomNumber;
    }

    @Transactional
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}