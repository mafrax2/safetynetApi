package com.openclassrooms.safetynetApi.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.openclassrooms.safetynetApi.model.FireStation;
import com.openclassrooms.safetynetApi.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FireStationRepository {

    public static void deleteFireStationByAddress(String address) throws IOException {

        JsonNode nodes = RepositoryUtil.extractNodes();
        ArrayNode firestations = (ArrayNode) nodes.path("firestations");
        ArrayList<Integer> ids = new ArrayList<>();
        Integer i = 0;
        for (Iterator<JsonNode> it = firestations.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            if (node.get("address").asText().equals(address) ){
                System.out.println(address+ " to be removed");
                ids.add(i);
            }
            i++;
        }

        ids.sort(Comparator.reverseOrder());
        for (Integer id: ids
        ) {
            System.out.println(firestations.get(id).path("address").asText() +" has been removed");
            firestations.remove(id);
        }

        RepositoryUtil.writeValuesInFile(nodes);

    }



    public static void deleteFireStationByNumber(int station) throws IOException {


        JsonNode nodes = RepositoryUtil.extractNodes();
        ArrayNode firestations = (ArrayNode) nodes.path("firestations");

        ArrayList<Integer> ids = new ArrayList<>();
        Integer i = 0;
        for (Iterator<JsonNode> it = firestations.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            if (node.get("station").asInt()==station ){
                System.out.println(station+ " to be removed");
                ids.add(i);
            }
            i++;
        }

        ids.sort(Comparator.reverseOrder());
        for (Integer id: ids
        ) {
            System.out.println(firestations.get(id).path("station").asInt() +" has been removed");
            firestations.remove(id);
        }


        RepositoryUtil.writeValuesInFile(nodes);

    }

    public static void editFireStationNumber(FireStation fireStation) throws IOException {


        JsonNode nodes = RepositoryUtil.extractNodes();
        ArrayNode fireStations = (ArrayNode) nodes.path("firestations");

        boolean found = false;

        for (Iterator<JsonNode> it = fireStations.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            if (node.get("address").asText().equals(fireStation.getAddress())){
                ((ObjectNode) node).put("address", fireStation.getAddress());
                ((ObjectNode) node).put("station", Integer.valueOf(fireStation.getStation()).toString())
                ;
                found = true;
            }

        }

        if(!found){
            ObjectNode jsonNodes = fireStations.addObject();
            jsonNodes.put("address", fireStation.getAddress());
            jsonNodes.put("station",Integer.valueOf(fireStation.getStation()).toString() );
        }

       RepositoryUtil.writeValuesInFile(nodes);

    }

    public static void addFireStation(FireStation fireStation) throws IOException {


        JsonNode nodes = RepositoryUtil.extractNodes();

        ArrayNode fireStations = (ArrayNode) nodes.path("firestations");
        ObjectNode jsonNodes = ((ArrayNode) fireStations).addObject();
        int station = fireStation.getStation();
        String s = Integer.valueOf(station).toString();

        jsonNodes.put("address", fireStation.getAddress());
        jsonNodes.put("station",s );

        RepositoryUtil.writeValuesInFile(nodes);
    }

    public List<FireStation> getFireStations() throws Exception {
        // read json and write to db
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/data.json");

        JsonNode nodes = mapper.readTree(inputStream);


        JsonNode firestations = nodes.path("firestations");
        FireStation[] fireStations = mapper.treeToValue(firestations, FireStation[].class);
        List<FireStation> stations = Arrays.asList(fireStations);
        inputStream.close();
        return stations;

    }

    public FireStation getFireStationByAddress(String address) throws Exception {

            List<FireStation> allFs = getFireStations();
            List<FireStation> FireStationList = allFs.stream().filter(p -> p.getAddress().equals(address)).collect(Collectors.toList());
            return !FireStationList.isEmpty() ? FireStationList.get(0) : null;
    }
}
