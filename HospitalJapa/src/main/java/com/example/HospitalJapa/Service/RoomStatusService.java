package com.example.HospitalJapa.Service;

import com.example.HospitalJapa.DTO.*;
import com.example.HospitalJapa.Model.*;
import com.example.HospitalJapa.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomStatusService {

    @Autowired private BedRepository bedRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private AdmissionLogRepository admissionLogRepository;


}