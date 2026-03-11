package com.example.HospitalJapa.Service;

import com.example.HospitalJapa.DTO.HospitalDTO;
import com.example.HospitalJapa.DTO.WardDTO;
import com.example.HospitalJapa.Model.Hospital;
import com.example.HospitalJapa.Model.Ward;
import com.example.HospitalJapa.Repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WardService {

    @Autowired private WardRepository wardRepository;
    @Autowired private RoomService roomService;



    public Ward getWardById(Long id) {
        return wardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ala não encontrada com id: " + id));
    }


    public WardDTO getWardDTOById(Long id) {
        Ward ward = getWardById(id);
        return convertToDTO(ward);
    }


    public List<Ward> getWardsByHospitalId(Long hospitalId) {
        return wardRepository.findByHospitalId(hospitalId);
    }


    @Transactional
    public void createWard(Hospital hospital, List<HospitalDTO.WardRequestDTO> wardDtos) {
        for (HospitalDTO.WardRequestDTO wardDto : wardDtos) {
            Ward ward = new Ward();
            ward.setSpecialty(wardDto.specialty());
            ward.setHospital(hospital);
            ward.setRooms(new ArrayList<>());

            Integer qtdQuartos = wardDto.quantidadeQuartos();
            Integer qtdLeitos = wardDto.quantidadeLeitosPorQuarto();


            ward.setQuantidadeQuartos(qtdQuartos);
            ward.setQuantidadeLeitosPorQuarto(qtdLeitos);


            if (qtdQuartos != null && qtdQuartos > 0) {
                roomService.createRoomsBulk(ward, wardDto.specialty().toString(), qtdQuartos, qtdLeitos);
            }

            hospital.getWards().add(ward);
        }
    }


    @Transactional
    public void deleteWard(Long id) {
        wardRepository.deleteById(id);
    }


    private WardDTO convertToDTO(Ward ward) {
        WardDTO dto = new WardDTO();
        dto.setId(ward.getId());
        dto.setSpecialty(String.valueOf(ward.getSpecialty()));
        dto.setHospitalId(ward.getHospital() != null ? ward.getHospital().getId() : null);
        return dto;
    }
}