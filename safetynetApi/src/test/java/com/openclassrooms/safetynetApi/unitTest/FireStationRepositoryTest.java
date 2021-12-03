package com.openclassrooms.safetynetApi.unitTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetApi.model.FireStation;
import com.openclassrooms.safetynetApi.model.MedicalRecord;
import com.openclassrooms.safetynetApi.repository.FireStationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class FireStationRepositoryTest {

    private static FireStationRepository firestationRepository;



    @BeforeAll
    public static void setUp(){

        var relPath = Paths.get("src", "test", "resources", "json", "testdata.json"); // src/test/resources/image.jgp
        var absPath = relPath.toFile().getAbsolutePath(); // /home/<user>/../<project-root>/src/test/resources/image.jpg
        var anyFileUnderThisPath = new File(absPath).exists();

        System.out.println(anyFileUnderThisPath);

        firestationRepository = new FireStationRepository(absPath, new ObjectMapper());
    }

    @AfterEach
    public void reset() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("persons", new Object[]{UnitTestUtil.createPersonJson()});
        map.put("firestations", new FireStation[]{UnitTestUtil.createFs()});
        map.put("medicalrecords", new MedicalRecord[]{UnitTestUtil.createMR()});

        // create object mapper instance
        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(Paths.get(firestationRepository.getResourceLink()).toFile(), map);

    }

    @Test
    public void getFireStationListTest() throws Exception {

        List<FireStation> FireStationList = firestationRepository.getFireStations();

        FireStation firestation1 = UnitTestUtil.createFs();
        List<FireStation> expected = new ArrayList<>();
        expected.add(firestation1);

        assertEquals(expected, FireStationList);

    }

    @Test
    public void editfirestationTest() throws Exception {

        FireStation firestation = UnitTestUtil.createFs();
        firestation.setStation(1);

        firestationRepository.editFireStationNumber(firestation);
        FireStation firestationExpected = firestationRepository.getFireStationByAddress("1509 Culver St");

        assertEquals(firestationExpected, firestation);


    }

    @Test
    public void addfirestationTest() throws Exception {

        FireStation firestation = new FireStation("addresse2", 5);
        firestationRepository.addFireStation(firestation);
        FireStation firestationByName = firestationRepository.getFireStationByAddress("addresse2");

        assertEquals(firestationByName, firestation);

    }

    @Test
    public void deletefirestationTest() throws Exception {

        firestationRepository.deleteFireStationByAddress("1509 Culver St");
        FireStation firestationByName = firestationRepository.getFireStationByAddress("1509 Culver St");

        assertNull(firestationByName);

    }




}
