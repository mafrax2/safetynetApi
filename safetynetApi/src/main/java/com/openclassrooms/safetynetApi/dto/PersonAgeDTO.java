package com.openclassrooms.safetynetApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonAgeDTO {
//    le nom, le numéro de téléphone, l'âge et les antécédents
//médicaux (médicaments, posologie et allergies) de chaque personne.
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private String[] medications;
    private String[] allergies;





}
