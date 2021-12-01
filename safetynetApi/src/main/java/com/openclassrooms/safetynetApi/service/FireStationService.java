package com.openclassrooms.safetynetApi.service;

import com.openclassrooms.safetynetApi.model.FireStation;
import com.openclassrooms.safetynetApi.repository.FireStationRepository;
import com.openclassrooms.safetynetApi.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FireStationService {

    @Autowired
    FireStationRepository fireStationRepository;

    public FireStation getFireStation(String address) throws Exception {
       return fireStationRepository.getFireStationByAddress(address);
    }

    public void addFireStation(FireStation fireStation) throws IOException {
       FireStationRepository.addFireStation(fireStation);
    }

    public void editFireStation(FireStation fireStation) throws IOException {
        FireStationRepository.editFireStationNumber(fireStation);
    }

    public void deleteFireStationbyAdress(String address) throws IOException {
        FireStationRepository.deleteFireStationByAddress(address);
    }

}
