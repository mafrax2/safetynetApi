package com.openclassrooms.safetynetApi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.openclassrooms.safetynetApi.model.ChildAlertDTO;
import com.openclassrooms.safetynetApi.model.FireStation;
import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.repository.FireStationRepository;
import com.openclassrooms.safetynetApi.repository.PersonRepositoryImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private PersonRepositoryImpl personRepository;
    private FireStationRepository fsRepository;

    public PersonService(PersonRepositoryImpl personRepository, FireStationRepository fsRepository) {
        this.personRepository = personRepository;
        this.fsRepository = fsRepository;
    }


    public List<Person> list() throws Exception {
        return personRepository.getPeopleList();
    }

    private List<Person> fireStationPeople(int n) throws Exception {

        List<Person> allPeople = personRepository.getPeopleList();
        List<FireStation> fireStations = fsRepository.getFireStations();

        List<FireStation> stationList = fireStations.stream().filter(s -> s.getStation() == n).collect(Collectors.toList());

        ArrayList<Person> people = new ArrayList<>();

        for (FireStation station : stationList
        ) {
            List<Person> collect = allPeople.stream().filter(p -> p.getAddress().equals(station.getAddress())).collect(Collectors.toList());
            people.addAll(collect);
        }

        return people;

    }

    private HashMap<String, Object> countAdultsChildren(List<Person> people){
        Mapper mapper = new Mapper();
        List<Person> children = mapper.getChildren(people);

        long adultCount = people.size()-children.size();

        HashMap<String, Object> map = new HashMap<>();
        map.put("adult count", adultCount);
        map.put("children count", children.size());
        return map;

    }





    public TreeMap<String, Object> firestationNumberPeopleCount(int n) throws Exception {
        List<Person> people = fireStationPeople(n);

        TreeMap<String, Object> treeMap = new TreeMap<>();
        HashMap<String, Object> countAdultsChildren = countAdultsChildren(people);
        treeMap.put("people list", people);
        treeMap.put("counts", countAdultsChildren);

        return treeMap;
    }

    public List<JsonNode> findChildAdress(String address) throws Exception {
        List<Person> allPeople = list();

        List<Person> peopleAtAddress = allPeople.stream().filter(c -> c.getAddress().equals(address)).collect(Collectors.toList());
        Mapper mapperDTO = new Mapper();
        List<Person> childrenAtAdress = mapperDTO.getChildren(peopleAtAddress);
        peopleAtAddress.removeAll(childrenAtAdress);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tree = mapper.valueToTree(childrenAtAdress);

        JsonNode tree1 = mapper.valueToTree(peopleAtAddress);


        List<JsonNode> nodeList = new ArrayList<>();

        for (JsonNode childNode: tree
        ) {
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.set("child", childNode);
            objectNode.set("family", tree1);
            nodeList.add(objectNode);
        }
        return nodeList;
    }


    public List<ChildAlertDTO> findChildAdress2(String address) throws Exception {
        List<Person> allPeople = list();

        List<Person> peopleAtAddress = allPeople.stream().filter(c -> c.getAddress().equals(address)).collect(Collectors.toList());
        Mapper mapper = new Mapper();
        List<Person> childrenAtAddress = mapper.getChildren(peopleAtAddress);

        List<ChildAlertDTO> alerts = new ArrayList<>();


        for (Person child : childrenAtAddress
             ) {
            ChildAlertDTO childAlertDTO = mapper.toDto(child, peopleAtAddress);
            alerts.add(childAlertDTO);
        }

        return alerts;
    }


}


