package com.openclassrooms.safetynetApi.controller;

import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping("/person")
    public Person getPerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName ) throws Exception {
        return personService.getPerson(firstName, lastName);
    }

    @PostMapping(value = "/person")
    public ResponseEntity<Void> addPerson(@RequestBody Person person) throws Exception {

        personService.addPerson(person);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .queryParam("firstName", person.getFirstName())
                .queryParam("lastName", person.getLastName())
                .buildAndExpand(personService.getPerson(person.getFirstName(), person.getLastName()))
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping(value = "/person")
    public void editPerson(@RequestBody Person person) throws Exception {

        personService.editPerson(person);

    }

    @DeleteMapping(value = "/person")
    public void deletePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) throws Exception {
        personService.deletePerson(firstName, lastName);
    }

}
