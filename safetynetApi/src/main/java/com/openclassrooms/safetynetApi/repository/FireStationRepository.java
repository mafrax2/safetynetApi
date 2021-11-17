package com.openclassrooms.safetynetApi.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynetApi.model.FireStation;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Repository
public class FireStationRepository {

    public List<FireStation> getFireStations() throws Exception {
        // read json and write to db
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/data.json");

        JsonNode nodes = mapper.readTree(inputStream);


        JsonNode firestations = nodes.path("firestations");
        FireStation[] fireStations = mapper.treeToValue(firestations, FireStation[].class);
        List<FireStation> stations = Arrays.asList(fireStations);

        return stations;

    }

}
