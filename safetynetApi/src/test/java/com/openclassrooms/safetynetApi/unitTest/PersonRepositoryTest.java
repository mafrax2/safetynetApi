package com.openclassrooms.safetynetApi.unitTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetApi.model.FireStation;
import com.openclassrooms.safetynetApi.model.MedicalRecord;
import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.repository.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class PersonRepositoryTest {

    private static PersonRepository personRepository;



    @BeforeAll
    public static void setUp(){

        var relPath = Paths.get("src", "test", "resources", "json", "testdata.json"); // src/test/resources/image.jgp
        var absPath = relPath.toFile().getAbsolutePath(); // /home/<user>/../<project-root>/src/test/resources/image.jpg
        var anyFileUnderThisPath = new File(absPath).exists();

        System.out.println(anyFileUnderThisPath);

        personRepository = new PersonRepository(absPath, new ObjectMapper());
    }

    @AfterEach
    public void reset() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("persons", new Object[]{UnitTestUtil.createPersonJson()});
        map.put("firestations", new FireStation[]{UnitTestUtil.createFs()});
        map.put("medicalrecords", new MedicalRecord[]{UnitTestUtil.createMR()});

        // create object mapper instance
        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(Paths.get(personRepository.getResourceLink()).toFile(), map);

    }

    @Test
    public void getPeopleListTest() throws Exception {

        List<Person> peopleList = personRepository.getPeopleList();

        Person person1 = UnitTestUtil.createPerson();
        List<Person> expected = new ArrayList<>();
        expected.add(person1);

        assertEquals(expected, peopleList);

    }

    @Test
    public void editPersonTest() throws Exception {

        Person person = UnitTestUtil.createPerson();
        person.setAddress("here");

        personRepository.editPerson(person);
        Person personByName = personRepository.getPersonByName("marc", "marc");

        assertEquals(personByName, person);


    }

    @Test
    public void addPersonTest() throws Exception {

        Person person = new Person("prenom", "nom", "adresse", "ville", 75, "tel", "email", null, null, null);
        personRepository.addPerson(person);
        Person personByName = personRepository.getPersonByName("prenom", "nom");

        assertEquals(personByName, person);

    }

    @Test
    public void deletePersonTest() throws Exception {

        personRepository.deletePerson("marc", "marc");
        Person personByName = personRepository.getPersonByName("marc", "marc");

        assertNull(personByName);

    }




}
