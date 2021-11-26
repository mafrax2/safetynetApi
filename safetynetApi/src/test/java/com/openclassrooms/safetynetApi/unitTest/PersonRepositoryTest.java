package com.openclassrooms.safetynetApi.unitTest;

import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class PersonRepositoryTest {

    @Test
    public void getPeopleListTest() throws Exception {
        PersonRepository personRepository = new PersonRepository();
        List<Person> peopleList = personRepository.getPeopleList();
//        assertTrue(peopleList.stream().anyMatch(p -> p.getFirstName().equals("marc")));
        assertFalse(peopleList.isEmpty());

    }

}
