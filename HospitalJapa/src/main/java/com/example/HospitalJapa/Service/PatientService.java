package com.example.HospitalJapa.Service;

import com.example.HospitalJapa.DTO.PatientDTO;
import com.example.HospitalJapa.DTO.PatientLocationDTO;
import com.example.HospitalJapa.Model.AdmissionLog;
import com.example.HospitalJapa.Model.Patient;
import com.example.HospitalJapa.Repository.AdmissionLogRepository;
import com.example.HospitalJapa.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AdmissionLogRepository admissionLogRepository;


    @Transactional
    public Patient createPatient(PatientDTO dto) {
        if (patientRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new RuntimeException("Paciente com CPF " + dto.getCpf() + " já existe");
        }

        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setCpf(dto.getCpf());
        patient.setPhone(dto.getPhone());
        patient.setIsHospitalized(false);

        return patientRepository.save(patient);
    }


    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com id: " + id));
    }


    public PatientDTO getPatientDTOById(Long id) {
        Patient patient = getPatientById(id);
        return convertToDTO(patient);
    }


    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patient -> new PatientDTO(
                        patient.getId(),
                        patient.getName(),
                        patient.getCpf(),
                        patient.getPhone(),
                        patient.getIsHospitalized()
                ))
                .collect(Collectors.toList());
    }


    @Transactional
    public Patient updatePatient(Long id, PatientDTO dto) {
        Patient patient = getPatientById(id);
        patient.setName(dto.getName());
        patient.setPhone(dto.getPhone());
        return patientRepository.save(patient);
    }


    @Transactional
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }


    public Patient getPatientByCpf(String cpf) {
        return patientRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com CPF: " + cpf));
    }


    private PatientDTO convertToDTO(Patient patient) {
        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setCpf(patient.getCpf());
        dto.setPhone(patient.getPhone());
        return dto;
    }

    public PatientLocationDTO localizarPaciente(Long patientId) {
        AdmissionLog log = admissionLogRepository.findActiveAdmissionByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente não localizado ou já recebeu alta."));

        return new PatientLocationDTO(
                log.getPatient().getId(),
                log.getPatient().getName(),
                log.getBed().getRoom().getWard().getHospital().getName(),
                log.getBed().getRoom().getWard().getSpecialty().toString(),
                log.getBed().getRoom().getRoomCode(),
                log.getBed().getBedNumber()
        );
    }
}
