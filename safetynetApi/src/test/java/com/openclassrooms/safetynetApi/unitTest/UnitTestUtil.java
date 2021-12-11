package com.openclassrooms.safetynetApi.unitTest;

import com.openclassrooms.safetynetApi.model.FireStation;
import com.openclassrooms.safetynetApi.model.MedicalRecord;
import com.openclassrooms.safetynetApi.model.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

public class UnitTestUtil {

    public static Person createPerson() throws ParseException {
        Date date = getDate("12/02/1992");

        String[] allergies = new String[1];
        allergies[0] = "nillacilan";
        String[] medication = new String[]{"aznol:350mg", "hydrapermazol:100mg"};
        Person person1 = new Person("marc", "marc", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com", date, allergies, medication);
        return person1;
    }

    public static  Map<String, Object> createCustomPersonJson(String firstName, String lastName, String address, String city, String zip, String phone, String email) throws ParseException {
        TreeMap<String, Object> personMap = new TreeMap<>();
        personMap.put("firstName", firstName);
        personMap.put("lastName", lastName);
        personMap.put("address", address);
        personMap.put("city", city);
        personMap.put("zip", zip);
        personMap.put("phone", phone);
        personMap.put("email", email);
        return personMap;
    }

    public static Map<String, Object> createPersonJson(){
        TreeMap<String, Object> personMap = new TreeMap<>();
        personMap.put("firstName", "marc");
        personMap.put("lastName", "marc");
        personMap.put("address", "1509 Culver St");
        personMap.put("city", "Culver");
        personMap.put("zip", "97451");
        personMap.put("phone", "841-874-6512");
        personMap.put("email", "jaboyd@email.com");
        return personMap;
    }

    private static Date getDate(String dateString) throws ParseException {

        Date date = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
        return date;
    }


    private static Date getBirthDateByAge(int age, Date date) throws ParseException {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -age);
        Date birthDate = calendar.getTime();

        return birthDate;
    }

    public static FireStation createFs(){
        FireStation fireStation = new FireStation();
        fireStation.setAddress("1509 Culver St");
        fireStation.setStation(3);
        return fireStation;
    }

    public static MedicalRecord createMR(){
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("marc");
        medicalRecord.setLastName("marc");
        medicalRecord.setBirthdate("12/02/1992");
        medicalRecord.setMedications(new String[]{"aznol:350mg", "hydrapermazol:100mg"});
        medicalRecord.setAllergies(new String[]{"nillacilan"});
        return medicalRecord;
    }
}
