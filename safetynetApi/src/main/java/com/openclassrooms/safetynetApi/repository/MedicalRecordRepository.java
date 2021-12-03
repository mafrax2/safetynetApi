package com.openclassrooms.safetynetApi.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.openclassrooms.safetynetApi.model.MedicalRecord;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Repository
@Log4j2
public class MedicalRecordRepository extends SafetynetApiRepository{


    public MedicalRecordRepository(String resourceLink, ObjectMapper mapper) {
        super(resourceLink, mapper);
    }

    public MedicalRecordRepository() {
        super();
    }

    public void deleteMedicalRecord(String firstName, String lastName) throws IOException {


        JsonNode nodes = extractNodes();
        ArrayNode medicalrecords = (ArrayNode) nodes.path("medicalrecords");

        ArrayList<Integer> ids = new ArrayList<>();
        Integer i = 0;
        for (Iterator<JsonNode> it = medicalrecords.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            if (node.get("firstName").asText().equals(firstName) &&  node.get("lastName").asText().equals(lastName) ){
                System.out.println(firstName+" "+ lastName+ " medical records to be removed");
                ids.add(i);
            }
            i++;
        }

        ids.sort(Comparator.reverseOrder());
        for (Integer id: ids
        ) {
            System.out.println(medicalrecords.get(id).path("firstName").asText()+" "+medicalrecords.get(id).path("lastName").asText() +" medical records has been removed");
            medicalrecords.remove(id);
        }


        writeValuesInFile(nodes);

    }

    public void editMedicalRecord(MedicalRecord medicalRecord) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode nodes = extractNodes();
        ArrayNode MedicalRecords = (ArrayNode) nodes.path("medicalrecords");


        for (Iterator<JsonNode> it = MedicalRecords.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            if (node.get("lastName").asText().equals(medicalRecord.getFirstName()) && node.get("lastName").asText().equals(medicalRecord.getLastName())){
                ((ObjectNode) node).put("birthdate", medicalRecord.getBirthdate());

                ((ObjectNode) node).set("medications", objectMapper.valueToTree(medicalRecord.getMedications()));

                ((ObjectNode) node).set("allergies", objectMapper.valueToTree(medicalRecord.getAllergies()));




            }

        }

       writeValuesInFile(nodes);

    }

    public void addMedicalRecord(MedicalRecord medicalRecord) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode nodes = extractNodes();
        ArrayNode medicalRecords = (ArrayNode) nodes.path("medicalrecords");
        ObjectNode jsonNodes = ((ArrayNode) medicalRecords).addObject();

        jsonNodes.put("firstName", medicalRecord.getFirstName());
        jsonNodes.put("lastName", medicalRecord.getLastName());
        jsonNodes.put("birthdate",medicalRecord.getBirthdate());

        JsonNode medicationsJsonNode = objectMapper.valueToTree(medicalRecord.getMedications());
        jsonNodes.set("medications", medicationsJsonNode);

        JsonNode allergiesJsonNode = objectMapper.valueToTree(medicalRecord.getAllergies());
        jsonNodes.set("allergies", allergiesJsonNode);

        writeValuesInFile(nodes);
    }

    public List<MedicalRecord> getMedicalRecords() throws Exception {


        JsonNode nodes = extractNodes();


        JsonNode medicalrecords = nodes.path("medicalrecords");
        MedicalRecord[] medicalRecordsArray = getMapper().treeToValue(medicalrecords, MedicalRecord[].class);
        List<MedicalRecord> stations = Arrays.asList(medicalRecordsArray);
        return stations;

    }

}
