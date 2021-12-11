package com.openclassrooms.safetynetApi.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.openclassrooms.safetynetApi.model.Person;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Repository
@Log4j2
public class PersonRepository extends SafetynetApiRepository {


    public PersonRepository(String resourceLink, ObjectMapper mapper) {
        super(resourceLink, mapper);
    }

    public PersonRepository() {
        super();
    }

    public List<Person> getPeopleList() throws Exception {

        JsonNode nodes = extractNodes();

        JsonNode persons = nodes.path("persons");
        Person[] treeToValue = this.getMapper().treeToValue(persons, Person[].class);
        List<Person> people = Arrays.asList(treeToValue);

        JsonNode medicalrecordsNodes = nodes.path("medicalrecords");
        for (JsonNode record : medicalrecordsNodes ) {
            List<Person> collect = people.stream().filter(p -> p.getFirstName().equals(record.path("firstName").asText()) && p.getLastName().equals(record.path("lastName").asText())).collect(Collectors.toList());
            if(collect.size()>1){
                System.out.println("duplicate");
                log.warn("found duplicate person " + collect.get(0));
            } else if (!collect.isEmpty()){
                Person person = collect.get(0);
                String text = record.path("birthdate").asText();
                Date date = new SimpleDateFormat("MM/dd/yyyy").parse(text);
                person.setBirthdate(date);
                person.setAllergies(this.getMapper().treeToValue(record.path("allergies"), String[].class));
                person.setMedication(this.getMapper().treeToValue(record.path("medications"), String[].class));
            }
        }

        return(people);
    }

    public void addPerson(Person person) throws IOException {

        JsonNode nodes = extractNodes();

        ArrayNode persons = (ArrayNode) nodes.path("persons");
        ObjectNode jsonNodes = ((ArrayNode) persons).addObject();
        jsonNodes.put("firstName", person.getFirstName());
        jsonNodes.put("lastName", person.getLastName());
        jsonNodes.put("address", person.getAddress());
        jsonNodes.put("city", person.getCity());
        jsonNodes.put("zip", person.getZip());
        jsonNodes.put("phone", person.getPhone());
        jsonNodes.put("email", person.getEmail());


        this.writeValuesInFile(nodes);
        log.info("person added : " + person);

    }

    public void editPerson(Person person) throws IOException {
        JsonNode nodes = this.extractNodes();

        JsonNode jsonNode = nodes.path("persons");
        if(jsonNode.isEmpty()){
            log.error("no person found");
            throw new IOException("no person found");
        }
        ArrayNode persons = (ArrayNode) jsonNode;

        for (Iterator<JsonNode> it = persons.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            if (node.get("firstName").asText().equals(person.getFirstName()) && node.get("lastName").asText().equals(person.getLastName())){
                ((ObjectNode) node).put("address", person.getAddress());
                ((ObjectNode) node).put("city", person.getCity());
                ((ObjectNode) node).put("zip", person.getZip());
                ((ObjectNode) node).put("phone", person.getPhone());
                ((ObjectNode) node).put("address", person.getAddress());
                ((ObjectNode) node).put("email", person.getEmail());

            }

        }

       writeValuesInFile(nodes);
        log.info("person edited : " + person);

    }

    public void deletePerson(String firstName, String lastName) throws IOException {
        JsonNode nodes = this.extractNodes();
        ArrayNode persons = (ArrayNode) nodes.path("persons");

        ArrayList<Integer> ids = new ArrayList<>();
        Integer i = 0;
        for (Iterator<JsonNode> it = persons.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            if (node.get("firstName").asText().equals(firstName) && node.get("lastName").asText().equals(lastName)){
                System.out.println(firstName+" "+lastName + " to be removed");
                ids.add(i);
            }
        i++;
        }

        log.info("found " + ids.size() +" persons to delete");

ids.sort(Comparator.reverseOrder());
        for (Integer id: ids
             ) {
           System.out.println(persons.get(id).path("firstName").asText() +" " + persons.get(id).path("lastName").asText()+ " has been removed");
            persons.remove(id);
        }

       this.writeValuesInFile(nodes);

        log.info("person deleted :" + firstName +" "+ lastName);

    }

    public Person getPersonByName(String firstname, String lastname) throws Exception {
        List<Person> allPeople = getPeopleList();
        List<Person> personList = allPeople.stream().filter(p -> p.getFirstName().equals(firstname) && p.getLastName().equals(lastname)).collect(Collectors.toList());
        return !personList.isEmpty() ? personList.get(0) : null;
    }




}
