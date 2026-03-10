package com.example.HospitalJapa.Service;

import com.example.HospitalJapa.DTO.HospitalDTO;
import com.example.HospitalJapa.Model.*;
import com.example.HospitalJapa.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalService {

    @Autowired private HospitalRepository hospitalRepository;
    @Autowired private WardRepository wardRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private BedRepository bedRepository;
    @Autowired private WardService wardService;


    public List<Hospital> listarTodos() {
        return hospitalRepository.findAll();
    }

    public Hospital buscarPorId(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital não encontrado com id: " + id));
    }

    @Transactional
    public Hospital createHospitalCompleto(HospitalDTO dto) {
        Hospital hospital = new Hospital();
        hospital.setName(dto.name());
        hospital.setPhone(dto.phone());
        hospital.setCnpj(dto.cnpj());
        hospital.setWards(new ArrayList<>());

        if (dto.wards() != null && !dto.wards().isEmpty()) {
            wardService.createWard(hospital, dto.wards());
        }

        return hospitalRepository.save(hospital);
    }

    @Transactional
    public Hospital createHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    @Transactional
    public Ward createWard(Long hospitalId, Ward ward) {
        Hospital hospital = buscarPorId(hospitalId);
        ward.setHospital(hospital);
        return wardRepository.save(ward);
    }

    @Transactional
    public Room createRoom(Long wardId, Room room) {
        Ward ward = wardRepository.findById(wardId)
                .orElseThrow(() -> new RuntimeException("Ala não encontrada com id: " + wardId));

        List<Room> existingRooms = roomRepository.findByWardId(wardId);
        int roomNumber = existingRooms.size() + 1;

        room.setWard(ward);
        String roomCode = generateRoomCode(ward.getSpecialty().toString(), roomNumber);
        room.setRoomCode(roomCode);
        room.setStatus("ACTIVE");

        return createRoomWithBeds(wardId, room);
    }

    @Transactional
    public Room createRoomWithBeds(Long wardId, Room room) {
        Ward ward = wardRepository.findById(wardId)
                .orElseThrow(() -> new RuntimeException("Ala não encontrada com id: " + wardId));

        room.setWard(ward);
        if (room.getRoomCode() == null || room.getRoomCode().isEmpty()) {
            List<Room> existingRooms = roomRepository.findByWardId(wardId);
            int roomNumber = existingRooms.size() + 1;
            room.setRoomCode(generateRoomCode(ward.getSpecialty().toString(), roomNumber));
        }
        if (room.getStatus() == null || room.getStatus().isEmpty()) {
            room.setStatus("ACTIVE");
        }

        Room roomSalvo = roomRepository.save(room);

        if (room.getBeds() != null && !room.getBeds().isEmpty()) {
            for (Bed bed : room.getBeds()) {
                bed.setRoom(roomSalvo);
                if (bed.getStatus() == null || bed.getStatus().isEmpty()) {
                    bed.setStatus("UNOCCUPIED");
                }
                bed.setPatient(null);
                bedRepository.save(bed);
            }
        }

        return roomSalvo;
    }


    private String generateRoomCode(String specialty, int roomNumber) {
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
    public Hospital updateHospital(Long id, Hospital hospitalAtualizado) {
        Hospital hospital = buscarPorId(id);
        hospital.setName(hospitalAtualizado.getName());
        hospital.setPhone(hospitalAtualizado.getPhone());
        hospital.setCnpj(hospitalAtualizado.getCnpj());
        return hospitalRepository.save(hospital);
    }

    public void deleteHospital(Long id) {
        hospitalRepository.deleteById(id);
    }
}