package com.example.HospitalJapa.Service;

import com.example.HospitalJapa.DTO.*;
import com.example.HospitalJapa.Model.Bed;
import com.example.HospitalJapa.Model.Room;
import com.example.HospitalJapa.Model.Ward;
import com.example.HospitalJapa.Repository.RoomRepository;
import com.example.HospitalJapa.Repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    public void createRoomsBulk(Ward ward, String specialty, Integer quantidadeQuartos, Integer quantidadeLeitosPorQuarto) {
        for (int i = 1; i <= quantidadeQuartos; i++) {
            Room room = new Room();
            room.setWard(ward);
            room.setRoomCode(generateRoomCode(specialty, i));
            room.setStatus("ACTIVE");
            room.setBeds(new ArrayList<>());

            if (quantidadeLeitosPorQuarto != null && quantidadeLeitosPorQuarto > 0) {
                for (Integer j = 1; j <= quantidadeLeitosPorQuarto; j++) {
                    Bed bed = new Bed();
                    bed.setRoom(room);
                    bed.setBedNumber(String.valueOf(j));
                    bed.setStatus("UNOCCUPIED");
                    room.getBeds().add(bed);
                }
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


    public List<SpecialtyStatusDTO> getRoomsStatsBySpecialty() {
        List<Room> allRooms = roomRepository.findAll();

        return allRooms.stream()
                .collect(Collectors.groupingBy(r -> r.getWard().getSpecialty()))
                .entrySet().stream()
                .map(entry -> {
                    String specialty = entry.getKey().toString();
                    List<Room> rooms = entry.getValue();
                    long total = rooms.size();
                    long occupied = rooms.stream()
                            .filter(r -> r.getBeds().stream()
                                    .anyMatch(b -> "OCCUPIED".equals(b.getStatus())))
                            .count();

                    long free = total - occupied;

                    return new SpecialtyStatusDTO(specialty, total, free, occupied);
                })
                .toList();
    }


    public List<RoomStatusDTO> getRoomStatusStats() {
        List<Room> allRooms = roomRepository.findAll();

        return allRooms.stream()
                .collect(Collectors.groupingBy(r -> r.getWard().getSpecialty().toString()))
                .entrySet().stream()
                .map(entry -> {
                    String specialty = entry.getKey();
                    List<Room> roomsBySpecialty = entry.getValue();

                    long total = roomsBySpecialty.size();
                    long occupied = roomsBySpecialty.stream()
                            .filter(room -> room.getBeds().stream()
                                    .anyMatch(bed -> "OCCUPIED".equals(bed.getStatus())))
                            .count();

                    long free = total - occupied;
                    return new RoomStatusDTO(specialty, total, free, occupied);
                })
                .toList();
    }


    public List<AvailableRoomDTO> getAvailableRooms() {
        List<Room> rooms = roomRepository.findRoomsWithAvailableBeds();

        return rooms.stream()
                .map(r -> new AvailableRoomDTO(
                        r.getWard().getSpecialty().toString(),
                        r.getRoomCode()
                ))
                .toList();
    }


}