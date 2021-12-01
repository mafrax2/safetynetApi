package com.openclassrooms.safetynetApi.controller;

import com.openclassrooms.safetynetApi.model.dto.*;
import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.service.SafetyAppService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SafetyNetController {

     private SafetyAppService safetyAppService;

    public SafetyNetController(SafetyAppService safetyAppService) {
        this.safetyAppService = safetyAppService;
    }

    @GetMapping(value = "/persons")
    public Iterable<Person> list() throws Exception {
        return safetyAppService.list();
    }

    @GetMapping("/firestation")
    @ResponseBody
    public FireStationChildrenCountDTO firestationPeople(@RequestParam("firestationNumber") int id) throws Exception {
        return  safetyAppService.firestationNumberPeopleCount(id);
    }

    @GetMapping("/phoneAlert")
    @ResponseBody
    public List<String> phoneAlert(@RequestParam("firestation") int id) throws Exception {
        return  safetyAppService.getPhonesToAlert(id);
    }



    @GetMapping("/childAlert")
    public List<ChildAlertDTO> childAlert(@RequestParam("address") String address) throws Exception {
        return  safetyAppService.findChildAdress(address);
    }

//    http://localhost:8080/fire?address=<address>
//    Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
//    de pompiers la desservant. La liste doit inclure le nom, le numéro de téléphone, l'âge et les antécédents
//    médicaux (médicaments, posologie et allergies) de chaque personne.
    @GetMapping("/fire")
    public FireDTO fire(@RequestParam("address") String address) throws Exception {
    return  safetyAppService.peopleToCallIfFire(address);
}

//    http://localhost:8080/flood/stations?stations=<a list of station_numbers>
//    Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper les
//    personnes par adresse. Elle doit aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et
//    faire figurer leurs antécédents médicaux (médicaments, posologie et allergies) à côté de chaque nom.
        @GetMapping("/flood")
        public List<FloodDTO> flood(@RequestParam("stations") int[] stations) throws Exception {
            return safetyAppService.getFloodList(stations);
        }

//    http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
//    Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
//    posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doivent
//    toutes apparaître
    @GetMapping("/personInfo")
    public List<PersonInfoDTO> personInfo(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName ) throws Exception {
        return safetyAppService.getPeopleByName(firstName, lastName);
    }
}
