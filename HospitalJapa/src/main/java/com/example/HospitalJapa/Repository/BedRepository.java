package com.example.HospitalJapa.Repository;

import com.example.HospitalJapa.Model.Bed;
import com.example.HospitalJapa.Model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BedRepository extends JpaRepository<Bed, Long> {
    List<Bed> findByRoomId(Long roomId);

    @Query("SELECT b FROM Bed b WHERE b.status = 'UNOCCUPIED' AND b.room.ward.specialty = :specialty")
    List<Bed> findAvailableBedsBySpecialty(@Param("specialty") Especialidade specialty);

}