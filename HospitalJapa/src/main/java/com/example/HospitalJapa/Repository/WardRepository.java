package com.example.HospitalJapa.Repository;

import com.example.HospitalJapa.Model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {
    List<Ward> findByHospitalId(Long hospitalId);

}

