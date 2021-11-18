package com.openclassrooms.safetynetApi.service;

import com.openclassrooms.safetynetApi.model.FireStation;
import com.openclassrooms.safetynetApi.model.dto.ChildAlertDTO;
import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.model.dto.FireDTO;
import com.openclassrooms.safetynetApi.model.dto.FirePersonDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public ChildAlertDTO toChildAlertDto(Person person, List<Person> house) {

        ChildAlertDTO alert = new ChildAlertDTO();
        alert.setFirstName(person.getFirstName());
        alert.setLastName(person.getLastName());
        alert.setAge(getAge(person.getBirthDate()));
        List<Person> collect = house.stream().filter(p -> p.getLastName().equals(person.getLastName()) && !p.getFirstName().equals(person.getFirstName())).collect(Collectors.toList());
        List<String> familyMember = collect.stream().map(c -> c.getFirstName() +' '+ c.getLastName()).collect(Collectors.toList());
        String[] household = familyMember.stream().toArray(String[]::new);
        alert.setHousehold(household);

        return alert;
    }

    public FireDTO toFireDTO(List<Person> persons, List<FireStation> fireStations){

        List<FirePersonDTO> firePersonDTOS = persons.stream().map(p -> new FirePersonDTO(p.getFirstName(), p.getLastName(), p.getPhone(), getAge(p.getBirthDate()), p.getMedication(), p.getAllergies())).collect(Collectors.toList());

        int stationNumber = 0;
        if(fireStations.size()>1){
            System.out.println("duplicate");
        } else {
            stationNumber = fireStations.get(0).getStation();
        }

        return new FireDTO(stationNumber, firePersonDTOS);

    }

    public List<Person> getChildren(List<Person> people) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -18);
        Date date = calendar.getTime();
        List<Person> children = people.stream().filter(p -> new Date(p.getBirthDate().getTime()).after(date)).collect(Collectors.toList());
        return children;
    }

    public int getAge(Date birthDate) {

        LocalDate localBirthDate = birthDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate localDate = (new Date()).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        int years = Period.between( localBirthDate, localDate).getYears();

        return years;
    }


}
