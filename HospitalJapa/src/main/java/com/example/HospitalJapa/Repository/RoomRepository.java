package com.example.HospitalJapa.Repository;

import com.example.HospitalJapa.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByWardId(Long wardId);

    @Query("SELECT DISTINCT r FROM Room r " + "JOIN r.beds b " + "WHERE b.status = 'UNOCCUPIED'")
    List<Room> findRoomsWithAvailableBeds();
}

