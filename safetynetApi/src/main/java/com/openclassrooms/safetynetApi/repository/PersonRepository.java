package com.openclassrooms.safetynetApi.repository;

import com.openclassrooms.safetynetApi.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Repository
public interface PersonRepository  {

    public List<Person> getPeopleList() throws Exception;

}
