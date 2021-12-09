package com.openclassrooms.safetynetApi.controller;

import com.openclassrooms.safetynetApi.model.FireStation;
import com.openclassrooms.safetynetApi.service.FireStationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Log4j2
public class FireStationController {

    @Autowired
    FireStationService fireStationService;

    @GetMapping(value = "/firestation", params = "address" )
    public FireStation getFireStation(@RequestParam String address) throws Exception {
        log.info("received GET request for /firestation?adress="+ address);
        return fireStationService.getFireStation(address);
    }

    @PostMapping(value = "/firestation")
    public ResponseEntity<Void> addFireStation(@RequestBody FireStation fireStation) throws Exception {

        log.info("received POST request at /firestation for :"+ fireStation);
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

        log.info("received PUT request at /firestation for :"+ fireStation);

        fireStationService.editFireStation(fireStation);

    }

    @DeleteMapping(value = "/firestation", params = "address")
    public void deleteFireStationByAdress(@RequestParam String address) throws Exception {

        log.info("received DELETE request for /firestation?adress="+ address);


        fireStationService.deleteFireStationbyAdress(address);
    }

    @DeleteMapping(value = "/firestation", params = "station")
    public void deleteFireStationByStation(@RequestParam int station) throws Exception {

        log.info("received DELETE request for /firestation?station="+ station);

        fireStationService.deleteFireStationbyNumber(station);
    }

}
