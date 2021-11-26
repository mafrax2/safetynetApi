package com.openclassrooms.safetynetApi.service;

import com.openclassrooms.safetynetApi.model.FireStation;
import com.openclassrooms.safetynetApi.model.dto.*;
import com.openclassrooms.safetynetApi.model.Person;
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

    public static FireStationChildrenCountDTO countAdultsChildren(List<Person> people){

        List<Person> children = getChildren(people);

        int adultCount = people.size()-children.size();

        return new FireStationChildrenCountDTO(children.size(), adultCount, people);

    }

    public static ChildAlertDTO toChildAlertDto(Person person, List<Person> house) throws Exception {

        if (getAge(person.getBirthDate())>18){
            throw new Exception("Please provide a child as first argument");
        }
        if(house.stream().anyMatch(p -> !p.getAddress().equals(person.getAddress()))) throw new Exception("Please provide a list of person living at the same address as second argument ");

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

    public static FireDTO toFireDTO(List<Person> persons, List<FireStation> fireStations){

        List<PersonAgeDTO> personAgeDTOS = getPersonAgeDTOS(persons);

        int stationNumber = 0;
        if(fireStations.size()>1){
            System.out.println("duplicate");
        } else {
            stationNumber = fireStations.get(0).getStation();
        }

        return new FireDTO(stationNumber, personAgeDTOS);

    }

    public static List<PersonAgeDTO> getPersonAgeDTOS(List<Person> persons) {
        List<PersonAgeDTO> personAgeDTOS = persons.stream().map(p -> new PersonAgeDTO(p.getFirstName(), p.getLastName(), p.getPhone(), getAge(p.getBirthDate()), p.getMedication(), p.getAllergies())).collect(Collectors.toList());
        return personAgeDTOS;
    }

    public static List<PersonInfoDTO> getPersonInfoDTOS(List<Person> persons) {
        List<PersonInfoDTO> personInfoDTO = persons.stream().map(p -> new PersonInfoDTO(p.getFirstName(), p.getLastName(), getAge(p.getBirthDate()), p.getEmail(), p.getMedication(), p.getAllergies())).collect(Collectors.toList());
        return personInfoDTO;
    }

    public static List<Person> getChildren(List<Person> people) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -18);
        Date date = calendar.getTime();
        List<Person> children = people.stream().filter(p -> new Date(p.getBirthDate().getTime()).after(date)).collect(Collectors.toList());
        return children;
    }

    public static int getAge(Date birthDate) {

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
