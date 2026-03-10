package com.example.HospitalJapa.DTO;

import com.example.HospitalJapa.Model.Room;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WardDTO {
    private Long id;
    private String specialty;
    private Long hospitalId;
    private List<Room> rooms;
}
