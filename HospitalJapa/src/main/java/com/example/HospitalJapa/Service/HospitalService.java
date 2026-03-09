package com.example.HospitalJapa.Service;

import com.example.HospitalJapa.Model.Hospital;
import com.example.HospitalJapa.Repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {

        @Autowired
        private HospitalRepository repository;

        public List<Hospital> listarTodos() {
            return repository.findAll();
        }

        public Hospital salvar(Hospital hospital) {
            return repository.save(hospital);
        }

        public void deletar(Long id) {
            repository.deleteById(id);
        }

        public Hospital buscarPorId(Long id) {
            return repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Hospital não encontrado"));
        }

        public Hospital atualizar(Long id, Hospital hospitalAtualizado) {
            Hospital hospital = buscarPorId(id);
            hospital.setNome(hospitalAtualizado.getNome());
            hospital.setTelefone(hospitalAtualizado.getTelefone());
            hospital.setCnpj(hospitalAtualizado.getCnpj());
            return repository.save(hospital);
        }


    }


