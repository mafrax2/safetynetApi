package com.openclassrooms.safetynetApi.unitTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class PersonRepositoryTest {

    @Test
    public void getPeopleListTest() throws Exception {

        PersonRepository personRepository = new PersonRepository("/json/testdata.json");
        List<Person> peopleList = personRepository.getPeopleList();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        String dateInString = "12-FEB-1992";

        Date date = formatter.parse(dateInString);

        String[] allergies = new String[1];
        allergies[0] = "nillacilan";
        String[] medication = new String[]{"aznol:350mg", "hydrapermazol:100mg"};
        Person person1 = new Person("marc", "marc", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com", date, allergies, medication);
        List<Person> expected = new ArrayList<>();
        expected.add(person1);

        assertEquals(expected, peopleList);

    }

}
