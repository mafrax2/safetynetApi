package com.openclassrooms.safetynetApi.unitTest;

import com.openclassrooms.safetynetApi.model.FireStation;
import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.model.dto.*;
import com.openclassrooms.safetynetApi.repository.FireStationRepository;
import com.openclassrooms.safetynetApi.repository.PersonRepository;
import com.openclassrooms.safetynetApi.service.Mapper;
import com.openclassrooms.safetynetApi.service.SafetyAppService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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
    private Date date;


    @BeforeEach
    private void setUpPerTest() throws Exception {
        date = new Date();
        people = createPersons();

        fireStations = createFireStations();


        safetyAppService = new SafetyAppService(personRepository, fsRepository);
    }

    private List<FireStation> createFireStations() {
        FireStation fireStation1 = new FireStation("here", 1);
        List<FireStation> fs = new ArrayList<>();
       fs.add(fireStation1);
        return fs;
    }

    private List<Person> createPersons() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -15);
        Date birthdate1 = calendar.getTime();
        String[] allergies = new String[1];
        allergies[0] = "alllergy1: allergy";
        String[] medication = new String[1];
        medication[0] = "medication1: 6g";
        Person person1 = new Person();
        person1.setBirthDate(birthdate1);
        person1.setAllergies(allergies);
        person1.setMedication(medication);
        person1.setAddress("here");
        person1.setCity("city");
        person1.setEmail("mail");
        person1.setFirstName("marc");
        person1.setLastName("marc");
        person1.setPhone("phone1");
        person1.setZip(719);


        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -30);
        Date birthdate2 = calendar.getTime();

        Person person2 = new Person("aurelie", "aurelie", "here", "city", 719, "phone2", "mail", birthdate2, allergies, medication);

        List<Person> people = new ArrayList<>();
        people.add(person1);
        people.add(person2);

        return people;
    }

    @Test
    public void fireStationPeopleSameAddressTest() throws Exception {
        //given
        when(personRepository.getPeopleList()).thenReturn(people);
        when(fsRepository.getFireStations()).thenReturn(fireStations);
        int stationNumber = 1;
        //when
        List<Person> people = safetyAppService.fireStationPeople(stationNumber);

        //then

        List<Person> persons = createPersons();
        assertTrue(persons.get(0).equals(people.get(0)));
        assertIterableEquals(persons, people);


    }

    @Test
    public void fireStationPeopleNoFireStationIdTest() throws Exception {
        when(personRepository.getPeopleList()).thenReturn(people);
        when(fsRepository.getFireStations()).thenReturn(fireStations);

        List<Person> persons = createPersons();
        assertThrows(Exception.class , () -> safetyAppService.fireStationPeople(2));
    }

    @Test
    public void fireStationAtAddressTest() throws Exception {
        when(fsRepository.getFireStations()).thenReturn(fireStations);
        List<FireStation> fs = safetyAppService.fireStationAtAddress("here");

        assertIterableEquals(fireStations, fs);
    }

    @Test
    public void firestationNumberPeopleCountTest() throws Exception {
        when(personRepository.getPeopleList()).thenReturn(people);
        when(fsRepository.getFireStations()).thenReturn(fireStations);
        FireStationChildrenCountDTO fireStationChildrenCountDTO = safetyAppService.firestationNumberPeopleCount(1);
        FireStationChildrenCountDTO dto = new FireStationChildrenCountDTO();
        dto.setAdultCount(1);
        dto.setChildrenCount(1);
        dto.setPeopleConcerned(people);
        assertEquals(1, fireStationChildrenCountDTO.getChildrenCount());
        assertEquals(1 , fireStationChildrenCountDTO.getAdultCount());
        assertIterableEquals(people , fireStationChildrenCountDTO.getPeopleConcerned());
        assertEquals(dto, fireStationChildrenCountDTO);
    }

    @Test
    public void findChildAddressTest() throws Exception {
        when(personRepository.getPeopleList()).thenReturn(people);
        List<ChildAlertDTO> childAlertDTOS = safetyAppService.findChildAdress("here");
        ChildAlertDTO childAlertDTO = new ChildAlertDTO();
        childAlertDTO.setAge(15);
        childAlertDTO.setFirstName("marc");
        childAlertDTO.setLastName("marc");
        childAlertDTO.setHousehold(new String[]{"aurelie aurelie"});
        assertEquals(childAlertDTO, childAlertDTOS.get(0));
    }

    @Test
    public void getPeopleAtAddressTest() throws Exception {
        when(personRepository.getPeopleList()).thenReturn(people);
        List<Person> peopleAtAddress = safetyAppService.getPeopleAtAddress("here");
        assertIterableEquals(people, peopleAtAddress);
    }

    @Test
    public void getPeopleByNameWithDuplicateTest() throws Exception {
        when(personRepository.getPeopleList()).thenReturn(people);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -15);
        Date birthdate1 = calendar.getTime();
        String[] allergies = new String[1];
        allergies[0] = "alllergy1: allergy";
        String[] medication = new String[1];
        medication[0] = "medication1: 6g";
        Person person1 = new Person("marc", "marc", "here", "city", 719, "phone", "mail", birthdate1, allergies, medication);


        people.add(person1);

        PersonInfoDTO personInfoDTO = Mapper.getPersonInfoDTO(people.get(0));
        PersonInfoDTO personInfoDTO1 = Mapper.getPersonInfoDTO(person1);
        List<PersonInfoDTO> dtos = new ArrayList<>();
        dtos.add(personInfoDTO);
        dtos.add(personInfoDTO1);

        List<PersonInfoDTO> peopleByName = safetyAppService.getPeopleByName("marc", "marc");

        assertIterableEquals(dtos, peopleByName);
    }

    @Test
    public void extractPhoneNumbersFromMapTest() throws Exception{
        HashMap<FireStation, List<Person>> map = new HashMap<>();
        map.put(fireStations.get(0), people);
        List<String> phones = safetyAppService.extractPhoneNumbersFromMap(map);

        ArrayList<String> phones1 = new ArrayList<>();
        phones1.add("phone1");
        phones1.add("phone2");

        assertIterableEquals(phones1, phones);
    }

    @Test
    public void peopleToCallIfFireWithDifferentAddressTest() throws Exception{

        when(personRepository.getPeopleList()).thenReturn(people);
        when(fsRepository.getFireStations()).thenReturn(fireStations);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -15);
        Date birthdate1 = calendar.getTime();
        String[] allergies = new String[1];
        allergies[0] = "alllergy1: allergy";
        String[] medication = new String[1];
        medication[0] = "medication1: 6g";
        Person person3 = new Person( "marc", "marc", "notHere", "city", 719, "phone", "mail", birthdate1, allergies, medication);


        people.add(person3);

        FireDTO people = safetyAppService.peopleToCallIfFire("here");

        FireDTO fireDTO = new FireDTO();
        fireDTO.setFiresstationNumber(1);
        List<Person> persons = createPersons();
        List<PersonAgeDTO> personAgeDTOS = Mapper.getPersonAgeDTOS(persons);
        fireDTO.setFirePersonAgeDTOS(personAgeDTOS);


        assertEquals(fireDTO, people);

    }

    @Test
    public void getFloodList() throws Exception{
        when(personRepository.getPeopleList()).thenReturn(people);
        when(fsRepository.getFireStations()).thenReturn(fireStations);
        int array[] = {1};
        List<FloodDTO> floodList = safetyAppService.getFloodList(array);

        FloodDTO floodDTO = new FloodDTO();
        floodDTO.setFiresstationAddress("here");
        List<PersonAgeDTO> personAgeDTOS = Mapper.getPersonAgeDTOS(people);
        floodDTO.setFirePersonDTOS(personAgeDTOS);

        List<FloodDTO> dtos = new ArrayList<>();
        dtos.add(floodDTO);

        assertIterableEquals(dtos, floodList);



    }

    @Test
    public void getPhonesToAlertTest() throws Exception {
        when(personRepository.getPeopleList()).thenReturn(people);
        when(fsRepository.getFireStations()).thenReturn(fireStations);
        List<String> phonesToAlert = safetyAppService.getPhonesToAlert(1);
        ArrayList<Object> phones = new ArrayList<>();
        phones.add("phone1");
        phones.add("phone2");
        assertEquals(phones, phonesToAlert);
    }

}
