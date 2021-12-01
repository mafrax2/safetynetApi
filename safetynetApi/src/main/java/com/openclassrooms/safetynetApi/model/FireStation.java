package com.openclassrooms.safetynetApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
public class FireStation {

    private String address;
    private int station;

    public FireStation() {
    }
}
