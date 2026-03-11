package com.example.HospitalJapa.Service;

import com.example.HospitalJapa.DTO.BedDTO;
import com.example.HospitalJapa.DTO.BedResponseDTO;
import com.example.HospitalJapa.Model.Bed;
import com.example.HospitalJapa.Model.Especialidade;
import com.example.HospitalJapa.Model.Room;
import com.example.HospitalJapa.Repository.BedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BedService {

    @Autowired
    private BedRepository bedRepository;



    public Bed getBedById(Long id) {
        return bedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leito não encontrado com id: " + id));
    }


    public BedDTO getBedDTOById(Long id) {
        Bed bed = getBedById(id);
        return convertToDTO(bed);
    }


    public List<BedDTO> getBedDTOsByRoomId(Long roomId) {
        return bedRepository.findByRoomId(roomId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public void createBeds(Room room, Integer quantidadeLeitos) {
        for (Integer j = 1; j <= quantidadeLeitos; j++) {
            Bed bed = new Bed();
            bed.setBedNumber(String.valueOf(j));
            bed.setStatus("UNOCCUPIED");
            bed.setRoom(room);
            bed.setPatient(null);

            room.getBeds().add(bed);
        }
    }


    @Transactional
    public void deleteBed(Long id) {
        bedRepository.deleteById(id);
    }


    private BedDTO convertToDTO(Bed bed) {
        BedDTO dto = new BedDTO();
        dto.setId(bed.getId());
        dto.setBedNumber(String.valueOf(bed.getBedNumber()));
        dto.setStatus(bed.getStatus());
        dto.setRoomId(bed.getRoom().getId());
        dto.setPatientId(bed.getPatient() != null ? bed.getPatient().getId() : null);
        return dto;
    }


    public List<BedResponseDTO> listarTodos() {
        return bedRepository.findAll().stream()
                .map(b -> new BedResponseDTO(
                        b.getId(),
                        b.getRoom().getWard().getHospital().getId(),
                        b.getBedNumber() != null ? b.getBedNumber(): "S/N",
                        b.getStatus(),
                        b.getRoom().getRoomCode()
                ))
                .toList();
    }


    public List<BedResponseDTO> listAvailableBySpecialty(Especialidade specialty) {
        List<Bed> beds = bedRepository.findAvailableBedsBySpecialty(specialty);

        return beds.stream()
                .map(b -> new BedResponseDTO(
                        b.getId(),
                        b.getRoom().getWard().getHospital().getId(),
                        b.getBedNumber() != null ? b.getBedNumber(): "S/N",
                        b.getStatus(),
                        b.getRoom().getRoomCode()
                ))
                .toList();
    }
}
