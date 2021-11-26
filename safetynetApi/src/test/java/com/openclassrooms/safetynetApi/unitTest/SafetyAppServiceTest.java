package com.openclassrooms.safetynetApi.unitTest;

import com.openclassrooms.safetynetApi.model.FireStation;
import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.model.dto.ChildAlertDTO;
import com.openclassrooms.safetynetApi.model.dto.FireStationChildrenCountDTO;
import com.openclassrooms.safetynetApi.model.dto.PersonInfoDTO;
import com.openclassrooms.safetynetApi.repository.FireStationRepository;
import com.openclassrooms.safetynetApi.repository.PersonRepository;
import com.openclassrooms.safetynetApi.service.Mapper;
import com.openclassrooms.safetynetApi.service.SafetyAppService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SafetyAppServiceTest {

    @Mock
    private static PersonRepository personRepository;
    @Mock
    private static FireStationRepository fsRepository;


    private Mapper mapperDTO;

    private SafetyAppService safetyAppService;

    private List<Person> people;
    private List<FireStation> fireStations;


    @BeforeEach
    private void setUpPerTest() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -15);
        Date birthdate1 = calendar.getTime();
        String[] allergies = new String[1];
        allergies[0] = "alllergy1: allergy";
        String[] medication = new String[1];
        medication[0] = "medication1: 6g";
        Person person1 = new Person(1L, "marc", "marc", "here", "city", 719, "phone", "mail", birthdate1, allergies, medication);

        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -30);
        Date birthdate2 = calendar.getTime();

        Person person2 = new Person(2L, "aurelie", "aurelie", "here", "city", 719, "phone", "mail", birthdate2, allergies, medication);

        people = new ArrayList<>();
        people.add(person1);
        people.add(person2);


        FireStation fireStation1 = new FireStation(1L, "here", 1);
        fireStations = new ArrayList<>();
        fireStations.add(fireStation1);


        safetyAppService = new SafetyAppService(personRepository, fsRepository);
    }

    @Test
    public void fireStationPeopleSameAddressTest() throws Exception {
        when(personRepository.getPeopleList()).thenReturn(people);
        when(fsRepository.getFireStations()).thenReturn(fireStations);
        List<Person> people = safetyAppService.fireStationPeople(1);

        assertEquals(2, people.size());
        assertTrue(people.stream().anyMatch(p -> p.getFirstName().equals("marc")));
        assertTrue(people.stream().anyMatch(p -> p.getFirstName().equals("aurelie")));

    }

    @Test
    public void fireStationPeopleNoFireStationIdTest() throws Exception {
        when(personRepository.getPeopleList()).thenReturn(people);
        when(fsRepository.getFireStations()).thenReturn(fireStations);
        assertThrows(Exception.class , () -> safetyAppService.fireStationPeople(2));
    }

    @Test
    public void fireStationAtAddressTest() throws Exception {
        when(fsRepository.getFireStations()).thenReturn(fireStations);
        List<FireStation> fs = safetyAppService.fireStationAtAddress("here");
        assertEquals(1, fs.size());
        assertEquals(1 , fs.get(0).getStation());
    }

    @Test
    public void firestationNumberPeopleCountTest() throws Exception {
        when(personRepository.getPeopleList()).thenReturn(people);
        when(fsRepository.getFireStations()).thenReturn(fireStations);
        FireStationChildrenCountDTO fireStationChildrenCountDTO = safetyAppService.firestationNumberPeopleCount(1);
        assertEquals(1, fireStationChildrenCountDTO.getChildrenCount());
        assertEquals(1 , fireStationChildrenCountDTO.getAdultCount());
        assertEquals(2 , fireStationChildrenCountDTO.getPeopleConcerned().size());
    }

    @Test
    public void findChildAddressTest() throws Exception {
        when(personRepository.getPeopleList()).thenReturn(people);
        List<ChildAlertDTO> childAlertDTOS = safetyAppService.findChildAdress("here");
        assertEquals(1 , childAlertDTOS.size());
        assertEquals("marc", childAlertDTOS.get(0).getFirstName());
        assertEquals(15, childAlertDTOS.get(0).getAge());
    }

    @Test
    public void getPeopleAtAddressTest() throws Exception {
        when(personRepository.getPeopleList()).thenReturn(people);
        List<Person> peopleAtAddress = safetyAppService.getPeopleAtAddress("here");
        assertEquals(2, peopleAtAddress.size());
    }

    @Test
    public void getPeopleByNameWithDuplicateTest() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -15);
        Date birthdate1 = calendar.getTime();
        String[] allergies = new String[1];
        allergies[0] = "alllergy1: allergy";
        String[] medication = new String[1];
        medication[0] = "medication1: 6g";
        Person person1 = new Person(1L, "marc", "marc", "here", "city", 719, "phone", "mail", birthdate1, allergies, medication);

        when(personRepository.getPeopleList()).thenReturn(people);

        people.add(person1);

        List<PersonInfoDTO> peopleByName = safetyAppService.getPeopleByName("marc", "marc");

        assertEquals(2 , peopleByName.size());

    }

}
