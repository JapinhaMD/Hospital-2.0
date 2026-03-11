package com.example.HospitalJapa.Repository;

import com.example.HospitalJapa.Model.Bed;
import com.example.HospitalJapa.Model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface BedRepository extends JpaRepository<Bed, Long> {
    List<Bed> findByRoomId(Long roomId);

    //psql, n eh a mesma coisa do sql
    @Query("SELECT b FROM Bed b WHERE b.status = 'UNOCCUPIED' AND b.room.ward.specialty = :specialty")
    List<Bed> findAvailableBedsBySpecialty(@Param("specialty") Especialidade specialty);

}