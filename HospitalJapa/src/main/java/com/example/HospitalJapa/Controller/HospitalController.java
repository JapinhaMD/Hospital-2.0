package com.example.HospitalJapa.Controller;
import com.example.HospitalJapa.Model.Hospital;
import com.example.HospitalJapa.Service.HospitalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hospital")

public class HospitalController {

    @Autowired
    private HospitalService service;

    @GetMapping
    public List<Hospital> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Hospital> criar(@Valid @RequestBody Hospital hospital) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(hospital));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospital> atualizar(@PathVariable Long id, @Valid @RequestBody Hospital hospital) {
        return ResponseEntity.ok(service.atualizar(id, hospital));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Hospital> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }


}
