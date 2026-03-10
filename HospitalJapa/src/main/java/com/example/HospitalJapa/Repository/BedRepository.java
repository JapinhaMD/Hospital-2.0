package com.example.HospitalJapa.Repository;

import com.example.HospitalJapa.Model.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface BedRepository extends JpaRepository<Bed, Long> {
    List<Bed> findByRoomId(Long roomId);

    @Query("SELECT b FROM Bed b WHERE b.room.id = :roomId AND b.status = 'UNOCCUPIED'")
    List<Bed> findAvailableBedsByRoomId(@Param("roomId") Long roomId);

    @Query("SELECT b FROM Bed b WHERE b.room.ward.id = :wardId AND b.status = 'UNOCCUPIED'")
    List<Bed> findAvailableBedsByWardId(@Param("wardId") Long wardId);

    @Query("SELECT b FROM Bed b WHERE b.patient.id = :patientId AND b.status = 'OCCUPIED'")
    Optional<Bed> findOccupiedBedByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT COUNT(b) FROM Bed b WHERE b.room.ward.id = :wardId AND b.status = 'UNOCCUPIED'")
    Long countAvailableBedsByWardId(@Param("wardId") Long wardId);

    @Query("SELECT COUNT(b) FROM Bed b WHERE b.room.ward.id = :wardId AND b.status = 'OCCUPIED'")
    Long countOccupiedBedsByWardId(@Param("wardId") Long wardId);

    @Query("SELECT COUNT(b) FROM Bed b WHERE b.room.ward.id = :wardId")
    Long countTotalBedsByWardId(@Param("wardId") Long wardId);
}
