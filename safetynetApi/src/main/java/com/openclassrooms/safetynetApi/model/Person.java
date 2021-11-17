package com.openclassrooms.safetynetApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @JsonIgnore
    private Long id;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
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
