package com.openclassrooms.safetynetApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class MedicalRecord {

    private String firstName;
    private String lastName;
    private String birthdate;
    private String[] medications;
    private String[] allergies;


    public MedicalRecord() {
    }
}
