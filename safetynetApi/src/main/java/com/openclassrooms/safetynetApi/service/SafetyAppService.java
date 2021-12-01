package com.openclassrooms.safetynetApi.service;

import com.openclassrooms.safetynetApi.model.dto.*;
import com.openclassrooms.safetynetApi.model.FireStation;
import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.repository.FireStationRepository;
import com.openclassrooms.safetynetApi.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SafetyAppService {

    private PersonRepository personRepository;
    private FireStationRepository fsRepository;

    public SafetyAppService(PersonRepository personRepository, FireStationRepository fsRepository) {
        this.personRepository = personRepository;
        this.fsRepository = fsRepository;
    }


    public List<Person> list() throws Exception {

        List<Person> peopleList = personRepository.getPeopleList();

        return peopleList;
    }

    public List<PersonInfoDTO> getPeopleByName(String firstName, String lastName) throws Exception{
        List<Person> allPeople = list();
        List<Person> personList = allPeople.stream().filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)).collect(Collectors.toList());
        return Mapper.getPersonInfoDTOS(personList);
    }

    public HashMap<FireStation, List<Person>> fireStationPeopleMap(int n) throws Exception {

        List<Person> allPeople = personRepository.getPeopleList();
        List<FireStation> fireStations = fsRepository.getFireStations();

        List<FireStation> stationList = fireStations.stream().filter(s -> s.getStation() == n).collect(Collectors.toList());

        HashMap<FireStation, List<Person>> map = new HashMap<>();

        for (FireStation station : stationList
        ) {

            List<Person> collect = allPeople.stream().filter(p -> p.getAddress().equals(station.getAddress())).collect(Collectors.toList());
            map.put(station, collect);
        }

        if (map.isEmpty()){
            throw new Exception("Either there is no FireStation with this number, or no people are covered by this FireStation");
        }

        return map;

    }

    private List<Person> extractPersonsFromMap(HashMap<FireStation, List<Person>> map){
        ArrayList<Person> allPersons = new ArrayList<>();

        for (Map.Entry<FireStation, List<Person>> entry : map.entrySet()
        ) {
            allPersons.addAll(entry.getValue());

        }
        return allPersons;
    }

    public List<Person> fireStationPeople(int n) throws Exception {
        HashMap<FireStation, List<Person>> fireStationListHashMap = fireStationPeopleMap(n);
        return extractPersonsFromMap(fireStationListHashMap);
    }

    public List<String> extractPhoneNumbersFromMap(HashMap<FireStation, List<Person>> map) {

        ArrayList<String> allPhones = new ArrayList<>();

        for (Map.Entry<FireStation, List<Person>> entry : map.entrySet()
        ) {
            allPhones.addAll(entry.getValue().stream().map(Person::getPhone).collect(Collectors.toList()));

        }
        return allPhones;
    }

    public List<String> getPhonesToAlert(int n) throws Exception {
        HashMap<FireStation, List<Person>> fireStationListMap = fireStationPeopleMap(n);
        return extractPhoneNumbersFromMap(fireStationListMap);
    }

    public List<FireStation> fireStationAtAddress(String address) throws Exception {

        List<FireStation> fireStations = fsRepository.getFireStations();
        List<FireStation> stationList = fireStations.stream().filter(s -> s.getAddress().equals(address)).collect(Collectors.toList());

        return stationList;

    }


    public FireStationChildrenCountDTO firestationNumberPeopleCount(int n) throws Exception {
        List<Person> people = fireStationPeople(n);

        FireStationChildrenCountDTO fireStationChildrenCountDTO = Mapper.countAdultsChildren(people);

        return fireStationChildrenCountDTO;
    }

    public List<ChildAlertDTO> findChildAdress(String address) throws Exception {
        List<Person> peopleAtAddress = getPeopleAtAddress(address);

        List<Person> childrenAtAddress = Mapper.getChildren(peopleAtAddress);

        List<ChildAlertDTO> alerts = new ArrayList<>();


        for (Person child : childrenAtAddress
             ) {
            ChildAlertDTO childAlertDTO = Mapper.toChildAlertDto(child, peopleAtAddress);
            alerts.add(childAlertDTO);
        }

        return alerts;
    }

    public List<Person> getPeopleAtAddress(String address) throws Exception {
        List<Person> allPeople = list();
        return allPeople.stream().filter(c -> c.getAddress().equals(address)).collect(Collectors.toList());
    }

    public FireDTO peopleToCallIfFire(String address) throws Exception {
        List<Person> peopleAtAddress = getPeopleAtAddress(address);
        List<FireStation> fireStations = fireStationAtAddress(address);
        return Mapper.toFireDTO(peopleAtAddress, fireStations);
    }


    public List<FloodDTO> getFloodList(int[] stations) throws Exception {

        ArrayList<FloodDTO> floodDTOs = new ArrayList<>();

        for (int station : stations
             ) {
            HashMap<FireStation, List<Person>> fireStationListHashMap = fireStationPeopleMap(station);
            for (Map.Entry<FireStation, List<Person>> entry: fireStationListHashMap.entrySet()
                 ) {

            List<PersonAgeDTO> personAgeDTOS = Mapper.getPersonAgeDTOS(entry.getValue());
            floodDTOs.add(new FloodDTO(entry.getKey().getAddress(), personAgeDTOS));
            }
        }

        return floodDTOs;
    }



}


