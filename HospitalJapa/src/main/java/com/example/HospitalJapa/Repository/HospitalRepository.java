package com.example.HospitalJapa.Repository;

import com.example.HospitalJapa.Model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository <Hospital, Long> {
Optional<Hospital> findById (Long id);
}
