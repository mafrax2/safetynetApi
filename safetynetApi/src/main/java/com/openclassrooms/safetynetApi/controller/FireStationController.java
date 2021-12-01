package com.openclassrooms.safetynetApi.controller;

import com.openclassrooms.safetynetApi.model.FireStation;
import com.openclassrooms.safetynetApi.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class FireStationController {

    @Autowired
    FireStationService fireStationService;

//    @GetMapping("/firestation")
//    public FireStation getFireStation(@RequestParam("address") String address) throws Exception {
//        return fireStationService.getFireStation(address);
//    }

    @PostMapping(value = "/firestation")
    public ResponseEntity<Void> addFireStation(@RequestBody FireStation fireStation) throws Exception {

        fireStationService.addFireStation(fireStation);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .queryParam("address", fireStation.getAddress())
                .queryParam("station", fireStation.getStation())
                .buildAndExpand(fireStationService.getFireStation(fireStation.getAddress()))
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping(value = "/firestation")
    public void editFireStation(@RequestBody FireStation fireStation) throws Exception {

        fireStationService.editFireStation(fireStation);

    }

    @DeleteMapping(value = "/firestation")
    public void deleteFireStationByAdress(@RequestParam("address") String address) throws Exception {
        fireStationService.deleteFireStationbyAdress(address);
    }

}