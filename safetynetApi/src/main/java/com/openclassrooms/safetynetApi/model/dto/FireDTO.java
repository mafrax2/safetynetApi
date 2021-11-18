package com.openclassrooms.safetynetApi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FireDTO {

    private int firesstationNumber;
    private List<FirePersonDTO> FirePersonDTOS;


}
