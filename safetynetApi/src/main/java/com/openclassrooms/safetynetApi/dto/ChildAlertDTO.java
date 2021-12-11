package com.openclassrooms.safetynetApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChildAlertDTO {

    String firstName;
    String lastName;
    int age;
    String[] household;

    public ChildAlertDTO() {
    }


}
