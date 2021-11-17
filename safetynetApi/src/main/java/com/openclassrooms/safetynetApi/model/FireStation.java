package com.openclassrooms.safetynetApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@Table(name = "stations")
public class FireStation {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;
    private String address;
    private int station;

    public FireStation() {
    }
}
