package com.openclassrooms.safetynetApi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.openclassrooms.safetynetApi.model.ChildAlertDTO;
import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.TreeMap;

@RestController
public class PersonController {
     private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/persons")
    public Iterable<Person> list() throws Exception {
        return personService.list();
    }

    @GetMapping("/firestation")
    @ResponseBody
    public TreeMap<String, Object> firestationPeople(@RequestParam("firestationNumber") int id) throws Exception {
        return  personService.firestationNumberPeopleCount(id);
    }
//    childAlert?address=<address>

    @GetMapping("/childAlert")
    @ResponseBody
    public List<JsonNode> childAlert(@RequestParam("address") String address) throws Exception {
        return  personService.findChildAdress(address);
    }

    @GetMapping("/childAlert2")
    public List<ChildAlertDTO> childAlert2(@RequestParam("address") String address) throws Exception {
        return  personService.findChildAdress2(address);
    }


}
