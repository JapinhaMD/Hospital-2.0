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