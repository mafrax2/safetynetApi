package com.openclassrooms.safetynetApi.unitTest;

import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.service.Mapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MapperTest {

    private static Person person;

    @BeforeAll
    private static void setup(){
        person = new Person();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -15);
        Date date = calendar.getTime();
        person.setBirthDate(date);

    }


    @Test
    public void getAgeTest()
    {
        Mapper mapper = new Mapper();
        int age = mapper.getAge(person.getBirthDate());
        assertEquals(15, age);
    }



}
