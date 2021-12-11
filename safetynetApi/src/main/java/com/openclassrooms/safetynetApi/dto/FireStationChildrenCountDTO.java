package com.openclassrooms.safetynetApi.dto;

import com.openclassrooms.safetynetApi.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FireStationChildrenCountDTO {
    private int childrenCount;
    private int adultCount;
    private List<Person> peopleConcerned;

    public FireStationChildrenCountDTO() {
    }
}
