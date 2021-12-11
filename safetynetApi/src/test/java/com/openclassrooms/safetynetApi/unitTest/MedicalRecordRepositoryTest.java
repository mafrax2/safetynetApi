package com.openclassrooms.safetynetApi.unitTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetApi.model.FireStation;
import com.openclassrooms.safetynetApi.model.MedicalRecord;
import com.openclassrooms.safetynetApi.repository.MedicalRecordRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class MedicalRecordRepositoryTest {

    private static MedicalRecordRepository medicalRecordRepository;



    @BeforeAll
    public static void setUp(){

        var relPath = Paths.get("src", "test", "resources", "json", "testdata.json"); // src/test/resources/image.jgp
        var absPath = relPath.toFile().getAbsolutePath(); // /home/<user>/../<project-root>/src/test/resources/image.jpg
        var anyFileUnderThisPath = new File(absPath).exists();

        System.out.println(anyFileUnderThisPath);

        medicalRecordRepository = new MedicalRecordRepository(absPath, new ObjectMapper());
    }

    @AfterEach
    public void reset() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("persons", new Object[]{UnitTestUtil.createPersonJson()});
        map.put("firestations", new FireStation[]{UnitTestUtil.createFs()});
        map.put("medicalrecords", new MedicalRecord[]{UnitTestUtil.createMR()});

        // create object mapper instance
        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(Paths.get(medicalRecordRepository.getResourceLink()).toFile(), map);

    }

    @Test
    public void getPeopleListTest() throws Exception {

        List<MedicalRecord> peopleList = medicalRecordRepository.getMedicalRecords();

        MedicalRecord MedicalRecord1 = UnitTestUtil.createMR();
        List<MedicalRecord> expected = new ArrayList<>();
        expected.add(MedicalRecord1);

        assertEquals(expected, peopleList);

    }

    @Test
    public void editMedicalRecordTest() throws Exception {

        MedicalRecord medicalRecord = UnitTestUtil.createMR();
        medicalRecord.setMedications(new String[]{});

        medicalRecordRepository.editMedicalRecord(medicalRecord);
        MedicalRecord medicalRecordByName = medicalRecordRepository.getMedicalRecords().stream().filter(m -> m.getFirstName().equals("marc") && m.getLastName().equals("marc")).findAny().get();

        assertEquals(medicalRecordByName, medicalRecord);


    }

    @Test
    public void addMedicalRecordTest() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord("prenom", "nom", null, new String[]{}, new String[]{});
        medicalRecordRepository.addMedicalRecord(medicalRecord);
        MedicalRecord medicalRecordByName = medicalRecordRepository.getMedicalRecords().stream().filter(m -> m.getFirstName().equals("prenom") && m.getLastName().equals("nom")).findAny().get();

        assertEquals(medicalRecordByName, medicalRecord);

    }

    @Test
    public void deleteMedicalRecordTest() throws Exception {

        medicalRecordRepository.deleteMedicalRecord("marc", "marc");
        assertFalse(medicalRecordRepository.getMedicalRecords().stream().filter(m -> m.getFirstName().equals("marc") && m.getLastName().equals("marc")).findAny().isPresent());


    }




}
