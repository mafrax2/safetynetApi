package com.openclassrooms.safetynetApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@Table(name = "records")
public class MedicalHistory {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;
//    private Person person;
    private String[] allergies;
    private String[] medication;


    public MedicalHistory() {
    }
}
