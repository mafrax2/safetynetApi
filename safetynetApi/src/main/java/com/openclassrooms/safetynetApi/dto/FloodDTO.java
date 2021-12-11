package com.openclassrooms.safetynetApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class FloodDTO {

    private String firesstationAddress;
    private List<PersonAgeDTO> FirePersonDTOS;

    public FloodDTO() {
    }
}
