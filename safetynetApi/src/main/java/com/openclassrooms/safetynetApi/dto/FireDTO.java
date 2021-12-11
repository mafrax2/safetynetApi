package com.openclassrooms.safetynetApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FireDTO {

    private int firesstationNumber;
    private List<PersonAgeDTO> FirePersonAgeDTOS;


    public FireDTO() {
    }
}