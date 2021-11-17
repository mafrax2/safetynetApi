package com.openclassrooms.safetynetApi.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class ChildAlertDTO {

    String firstName;
    String lastName;
    int age;
    String[] household;

    public ChildAlertDTO() {
    }

    public ChildAlertDTO(String firstName, String lastName, int age, String[] household) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.household = household;
    }
}
