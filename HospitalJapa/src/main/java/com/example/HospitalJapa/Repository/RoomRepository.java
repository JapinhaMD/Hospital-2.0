package com.example.HospitalJapa.Repository;

import com.example.HospitalJapa.DTO.RoomStatusDTO;
import com.example.HospitalJapa.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByWardId(Long wardId);

    @Query("SELECT new com.example.HospitalJapa.DTO.RoomStatusDTO(w.specialty, r.status, COUNT(r)) " +
            "FROM Room r JOIN r.ward w " +
            "GROUP BY w.specialty, r.status")
    List<RoomStatusDTO> countRoomsBySpecialtyAndStatus();
}

