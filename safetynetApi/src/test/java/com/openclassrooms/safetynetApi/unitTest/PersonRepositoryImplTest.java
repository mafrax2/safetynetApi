package com.openclassrooms.safetynetApi.unitTest;

import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.repository.PersonRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class PersonRepositoryImplTest {

    @Test
    public void getPeopleListTest() throws Exception {
        PersonRepositoryImpl personRepository = new PersonRepositoryImpl();
        List<Person> peopleList = personRepository.getPeopleList();
        assertFalse(peopleList.isEmpty());

    }

}
