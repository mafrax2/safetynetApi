package com.openclassrooms.safetynetApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonInfoDTO {
    private String firstName;
    private String lastName;
    private int age;
    private String mail;
    private String[] medications;
    private String[] allergies;
}
