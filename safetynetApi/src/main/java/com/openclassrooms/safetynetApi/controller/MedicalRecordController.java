
package com.openclassrooms.safetynetApi.controller;

import com.openclassrooms.safetynetApi.model.MedicalRecord;
import com.openclassrooms.safetynetApi.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class MedicalRecordController {

    @Autowired
    MedicalRecordService medicalRecordService;

//    @GetMapping("/MedicalRecord")
//    public MedicalRecord getMedicalRecord(@RequestParam("address") String address) throws Exception {
//        return MedicalRecordService.getMedicalRecord(address);
//    }

    @PostMapping(value = "/medicalrecord")
    public ResponseEntity<Void> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws Exception {

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

        medicalRecordService.editMedicalRecord(medicalRecord);

    }

    @DeleteMapping(value = "/medicalrecord")
    public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) throws Exception {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }


}
