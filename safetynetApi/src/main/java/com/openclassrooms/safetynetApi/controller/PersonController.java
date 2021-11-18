package com.openclassrooms.safetynetApi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.openclassrooms.safetynetApi.model.dto.ChildAlertDTO;
import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.model.dto.FireDTO;
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

    @GetMapping("/phoneAlert")
    @ResponseBody
    public String[] phoneAlert(@RequestParam("firestation") int id) throws Exception {
        return  personService.fireStationPeople(id).stream().map(Person::getPhone).distinct().toArray(String[]::new);
    }

    @GetMapping("/childAlert")
    @ResponseBody
    public List<JsonNode> childAlert(@RequestParam("address") String address) throws Exception {
        return  personService.findChildAdress(address);
    }

    @GetMapping("/childAlert2")
    public List<ChildAlertDTO> childAlert2(@RequestParam("address") String address) throws Exception {
        return  personService.findChildAdress2(address);
    }

//    http://localhost:8080/fire?address=<address>
//    Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
//    de pompiers la desservant. La liste doit inclure le nom, le numéro de téléphone, l'âge et les antécédents
//    médicaux (médicaments, posologie et allergies) de chaque personne.
@GetMapping("/fire")
public FireDTO fire(@RequestParam("address") String address) throws Exception {
    return  personService.peopleToCallIfFire(address);
}

}
