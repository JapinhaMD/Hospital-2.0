package com.example.HospitalJapa.Repository;

import com.example.HospitalJapa.Model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository <Hospital, Long> {
Optional<Hospital> findById (Long id);
}
