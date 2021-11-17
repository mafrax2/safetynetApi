package com.openclassrooms.safetynetApi.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetApi.model.FireStation;
import com.openclassrooms.safetynetApi.model.Person;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PersonRepositoryImpl implements PersonRepository{

    @Override
    public List<Person> getPeopleList() throws Exception {
        // read json and write to db
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/data.json");

        JsonNode nodes = mapper.readTree(inputStream);


        JsonNode persons = nodes.path("persons");
        Person[] treeToValue = mapper.treeToValue(persons, Person[].class);
        List<Person> people = Arrays.asList(treeToValue);

        JsonNode medicalrecordsNodes = nodes.path("medicalrecords");
        for (JsonNode record : medicalrecordsNodes ) {
            List<Person> collect = people.stream().filter(p -> p.getFirstName().equals(record.path("firstName").asText()) && p.getLastName().equals(record.path("lastName").asText())).collect(Collectors.toList());
            if(collect.size()>1){
                System.out.println("duplicate");
            } else {
                Person person = collect.get(0);
                String text = record.path("birthdate").asText();
                Date date = new SimpleDateFormat("MM/dd/yyyy").parse(text);
                person.setBirthDate(date);
                person.setAllergies(mapper.treeToValue(record.path("allergies"), String[].class));
                person.setMedication(mapper.treeToValue(record.path("medications"), String[].class));
            }
        }

        return(people);
    }




}
