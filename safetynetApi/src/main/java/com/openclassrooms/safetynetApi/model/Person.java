package com.openclassrooms.safetynetApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Person {


    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private int zip;
    private String phone;
    private String email;
    private Date birthDate;
    private String[] allergies;
    private String[] medication;

    public Person() {
    }


}
