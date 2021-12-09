
package com.openclassrooms.safetynetApi.controller;

import com.openclassrooms.safetynetApi.model.MedicalRecord;
import com.openclassrooms.safetynetApi.service.MedicalRecordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Log4j2
public class MedicalRecordController {

    @Autowired
    MedicalRecordService medicalRecordService;


    @PostMapping(value = "/medicalrecord")
    public ResponseEntity<Void> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws Exception {
        log.info("received POST request at /medicalrecord for:" + medicalRecord);
        medicalRecordService.addMedicalRecord(medicalRecord);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .queryParam("firstName", medicalRecord.getFirstName())
                .queryParam("lastName", medicalRecord.getLastName())
                .buildAndExpand(medicalRecordService.getMedicalRecord())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping(value = "/medicalrecord")
    public void editMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws Exception {
        log.info("received PUT request at /medicalrecord for:" + medicalRecord);
        medicalRecordService.editMedicalRecord(medicalRecord);

    }

    @DeleteMapping(value = "/medicalrecord")
    public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) throws Exception {
        log.info("received DELETE request for /medicalrecord?firstName="+firstName+"&lastName="+lastName);
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }


}
