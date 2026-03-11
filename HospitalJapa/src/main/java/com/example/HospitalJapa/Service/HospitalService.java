package com.example.HospitalJapa.Service;

import com.example.HospitalJapa.DTO.*;
import com.example.HospitalJapa.Model.Hospital;
import com.example.HospitalJapa.Repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalService {

    @Autowired private HospitalRepository hospitalRepository;
    @Autowired private WardService wardService;

    public List<Hospital> listarTodos() {
        return hospitalRepository.findAll();
    }


    public Hospital buscarPorId(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital não encontrado com id: " + id));
    }


    @Transactional
    public HospitalResponseDTO createHospital(HospitalDTO dto) {
        Hospital hospital = new Hospital();
        hospital.setName(dto.name());
        hospital.setPhone(dto.phone());
        hospital.setCnpj(dto.cnpj());
        hospital.setWards(new ArrayList<>());

        if (dto.wards() != null) {
            wardService.createWard(hospital, dto.wards());
        }


        Hospital salvo = hospitalRepository.saveAndFlush(hospital);

        return convertToResponseDTO(salvo);
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


    public HospitalResponseDTO convertToResponseDTO(Hospital hospital) {
        List<WardResponseDTO> wardDTOs = hospital.getWards().stream()
                .map(w -> new WardResponseDTO(
                        w.getId(),
                        w.getSpecialty() != null ? w.getSpecialty().toString() : null,
                        hospital.getId(),
                        w.getQuantidadeQuartos(),
                        w.getQuantidadeLeitosPorQuarto(),
                        w.getRooms().stream().map(r -> new RoomResponseDTO(
                                r.getId(),
                                r.getRoomCode(),
                                r.getStatus(),
                                r.getBeds().stream()
                                        .map(b -> new BedResponseDTO(b.getId(),b.getRoom().getWard().getHospital().getId(),b.getBedNumber(), b.getStatus(), b.getRoom().getWard().getSpecialty().toString()))
                                        .toList()
                        )).toList()
                )).toList();

        return new HospitalResponseDTO(
                hospital.getId(), hospital.getName(), hospital.getPhone(), hospital.getCnpj(), wardDTOs
        );
    }
}