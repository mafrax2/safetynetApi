package com.openclassrooms.safetynetApi.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.openclassrooms.safetynetApi.model.FireStation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Log4j2
public class FireStationRepository extends SafetynetApiRepository{


    public FireStationRepository(String resourceLink, ObjectMapper mapper) {
        super(resourceLink, mapper);
    }

    public FireStationRepository() {
    super();
    }


    public void deleteFireStationByAddress(String address) throws IOException {

        JsonNode nodes = extractNodes();
        ArrayNode firestations = (ArrayNode) nodes.path("firestations");
        ArrayList<Integer> ids = new ArrayList<>();
        Integer i = 0;
        for (Iterator<JsonNode> it = firestations.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            if (node.get("address").asText().equals(address) ){
                log.info(address+ " to be removed");
                ids.add(i);
            }
            i++;
        }

        ids.sort(Comparator.reverseOrder());
        for (Integer id: ids
        ) {
            log.info(firestations.get(id).path("address").asText() +" has been removed");
            firestations.remove(id);
        }

        writeValuesInFile(nodes);

    }



    public void deleteFireStationByNumber(int station) throws IOException {


        JsonNode nodes = extractNodes();
        ArrayNode firestations = (ArrayNode) nodes.path("firestations");

        ArrayList<Integer> ids = new ArrayList<>();
        Integer i = 0;
        for (Iterator<JsonNode> it = firestations.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            if (node.get("station").asInt()==station ){

                log.info(station+ " to be removed");
                ids.add(i);
            }
            i++;
        }

        ids.sort(Comparator.reverseOrder());
        for (Integer id: ids
        ) {

            log.info(firestations.get(id).path("station").asInt() +" has been removed");

            firestations.remove(id);
        }


        writeValuesInFile(nodes);

    }

    public void editFireStationNumber(FireStation fireStation) throws IOException {


        JsonNode nodes = extractNodes();
        ArrayNode fireStations = (ArrayNode) nodes.path("firestations");


        for (Iterator<JsonNode> it = fireStations.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            if (node.get("address").asText().equals(fireStation.getAddress())){
                ((ObjectNode) node).put("address", fireStation.getAddress());
                ((ObjectNode) node).put("station", Integer.valueOf(fireStation.getStation()).toString())
                ;

            }

        }

       writeValuesInFile(nodes);
       log.info("firestation has been edited: "+ fireStation );
    }

    public void addFireStation(FireStation fireStation) throws IOException {


        JsonNode nodes = extractNodes();

        ArrayNode fireStations = (ArrayNode) nodes.path("firestations");
        ObjectNode jsonNodes = ((ArrayNode) fireStations).addObject();
        int station = fireStation.getStation();
        String s = Integer.valueOf(station).toString();

        jsonNodes.put("address", fireStation.getAddress());
        jsonNodes.put("station",s );

        writeValuesInFile(nodes);
        log.info("firestation has been added: "+ fireStation );
    }

    public List<FireStation> getFireStations() throws Exception {
        // read json and write to db
        JsonNode nodes = extractNodes();



        JsonNode firestations = nodes.path("firestations");
        FireStation[] fireStations = this.getMapper().treeToValue(firestations, FireStation[].class);
        List<FireStation> stations = Arrays.asList(fireStations);

        return stations;

    }

    public FireStation getFireStationByAddress(String address) throws Exception {

            List<FireStation> allFs = getFireStations();
            List<FireStation> FireStationList = allFs.stream().filter(p -> p.getAddress().equals(address)).collect(Collectors.toList());
            return !FireStationList.isEmpty() ? FireStationList.get(0) : null;
    }
}
