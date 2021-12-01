package com.openclassrooms.safetynetApi.service;

import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public Person getPerson(String firstname, String lastname) throws Exception {
       return personRepository.getPersonByName(firstname, lastname);
    }

    public void addPerson(Person person) throws IOException {
       personRepository.addPerson(person);
    }

    public void editPerson(Person person) throws IOException {
        personRepository.editPerson(person);
    }

    public void deletePerson(String firstname, String lastname) throws IOException {
        personRepository.deletePerson(firstname,lastname);
    }

}
