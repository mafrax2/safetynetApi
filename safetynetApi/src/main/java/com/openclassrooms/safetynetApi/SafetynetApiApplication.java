package com.openclassrooms.safetynetApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetApi.model.FireStation;
import com.openclassrooms.safetynetApi.model.Person;
import com.openclassrooms.safetynetApi.service.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.InputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SafetynetApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetynetApiApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(PersonService personService) {
		return args -> {
			// read json and write to db
			ObjectMapper mapper = new ObjectMapper();
//			TypeReference<List<Person>> typeReference = new TypeReference<List<Person>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/data.json");
//			try {
//				List<Person> users = mapper.readValue(inputStream,typeReference);
//				personService.save(users);
//				System.out.println("Users Saved!");
//			} catch (IOException e){
//				System.out.println("Unable to save users: " + e.getMessage());
//			}



			JsonNode nodes = mapper.readTree(inputStream);


			JsonNode persons = nodes.path("persons");
			Person[] treeToValue = mapper.treeToValue(persons, Person[].class);
			List<Person> people = Arrays.asList(treeToValue);

			JsonNode firestations = nodes.path("firestations");
			FireStation[] fireStations = mapper.treeToValue(firestations, FireStation[].class);
			List<FireStation> stations = Arrays.asList(fireStations);

			JsonNode medicalrecordsNodes = nodes.path("medicalrecords");
			for (JsonNode record : medicalrecordsNodes ) {
				List<Person> collect = people.stream().filter(p -> p.getFirstName().equals(record.path("firstName").asText()) && p.getLastName().equals(record.path("lastName").asText())).collect(Collectors.toList());
				if(collect.size()>1){
					System.out.println("duplicate");
				} else {
					Person person = collect.get(0);
					String text = record.path("birthdate").asText();
					Date date = new SimpleDateFormat("MM/dd/yyyy").parse(text);
					person.setBirthDate(date);
					person.setAllergies(mapper.treeToValue(record.path("allergies"), String[].class));
					person.setMedication(mapper.treeToValue(record.path("medications"), String[].class));
				}
			}
//			personService.save(people);
//				System.out.println("Users Saved!");

		};
	}
}

