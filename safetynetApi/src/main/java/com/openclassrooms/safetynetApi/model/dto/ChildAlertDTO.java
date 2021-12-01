package com.openclassrooms.safetynetApi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

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
