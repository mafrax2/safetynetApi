package com.openclassrooms.safetynetApi.controller;

import com.openclassrooms.safetynetApi.model.dto.*;
import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.service.SafetyAppService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
public class SafetyNetController {

     private SafetyAppService safetyAppService;

    public SafetyNetController(SafetyAppService safetyAppService) {
        this.safetyAppService = safetyAppService;
    }

    @GetMapping(value = "/persons")
    public Iterable<Person> list() throws Exception {
        log.info("received GET request for /persons");
        return safetyAppService.list();
    }


//    http://localhost:8080/firestation?stationNumber=<station_number>
//    Cette url doit retourner une liste des personnes couvertes par la caserne de pompiers correspondante.
//            Donc, si le numéro de station = 1, elle doit renvoyer les habitants couverts par la station numéro 1. La liste
//    doit inclure les informations spécifiques suivantes : prénom, nom, adresse, numéro de téléphone. De plus,
//    elle doit fournir un décompte du nombre d'adultes et du nombre d'enfants (tout individu âgé de 18 ans ou
//            moins) dans la zone desservie
    @GetMapping("/firestation")
    @ResponseBody
    public FireStationChildrenCountDTO firestationPeople(@RequestParam("firestationNumber") int id) throws Exception {
        log.info("received GET request for /firestation?firestationNumber="+ id);
        return  safetyAppService.firestationNumberPeopleCount(id);
    }

//    http://localhost:8080/phoneAlert?firestation=<firestation_number>
//    Cette url doit retourner une liste des numéros de téléphone des résidents desservis par la caserne de
//    pompiers. Nous l'utiliserons pour envoyer des messages texte d'urgence à des foyers spécifiques.

    @GetMapping("/phoneAlert")
    @ResponseBody
    public List<String> phoneAlert(@RequestParam("firestation") int id) throws Exception {
        log.info("received GET request for /phoneAlert?firestation="+ id);
        return  safetyAppService.getPhonesToAlert(id);
    }


//    http://localhost:8080/childAlert?address=<address>
//    Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
//    La liste doit comprendre le prénom et le nom de famille de chaque enfant, son âge et une liste des autres
//    membres du foyer. S'il n'y a pas d'enfant, cette url peut renvoyer une chaîne vide.

    @GetMapping("/childAlert")
    public List<ChildAlertDTO> childAlert(@RequestParam("address") String address) throws Exception {
        log.info("received GET request for /childAlert?address="+ address);
        return  safetyAppService.findChildAdress(address);
    }

//    http://localhost:8080/fire?address=<address>
//    Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
//    de pompiers la desservant. La liste doit inclure le nom, le numéro de téléphone, l'âge et les antécédents
//    médicaux (médicaments, posologie et allergies) de chaque personne.
    @GetMapping("/fire")
    public FireDTO fire(@RequestParam("address") String address) throws Exception {
        log.info("received GET request for /fire?address="+ address);
    return  safetyAppService.peopleToCallIfFire(address);
}

//    http://localhost:8080/flood/stations?stations=<a list of station_numbers>
//    Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper les
//    personnes par adresse. Elle doit aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et
//    faire figurer leurs antécédents médicaux (médicaments, posologie et allergies) à côté de chaque nom.
        @GetMapping("/flood")
        public List<FloodDTO> flood(@RequestParam("stations") int[] stations) throws Exception {
            log.info("received GET request for /flood?stations="+ stations);
            return safetyAppService.getFloodList(stations);
        }

//    http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
//    Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
//    posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doivent
//    toutes apparaître
    @GetMapping("/personInfo")
    public List<PersonInfoDTO> personInfo(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName ) throws Exception {
        log.info("received GET request for /personInfo?firstName="+ firstName+"&lastName="+lastName);
        return safetyAppService.getPeopleByName(firstName, lastName);
    }

//    http://localhost:8080/communityEmail?city=<city>
//    Cette url doit retourner les adresses mail de tous les habitants de la ville.
    @GetMapping("communityEmail")
    public String[] communityEmail(@RequestParam("city") String city) throws Exception {
        log.info("received GET request for /communityEmail?city="+ city);
        return safetyAppService.getCityPhones(city);
    }

}
