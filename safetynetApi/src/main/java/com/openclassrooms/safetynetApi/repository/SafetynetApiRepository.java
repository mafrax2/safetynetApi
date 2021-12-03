package com.openclassrooms.safetynetApi.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Data
@AllArgsConstructor
@Repository
public class SafetynetApiRepository {

    @Value("${json.path:}")
    private String resourceLink;
    private ObjectMapper mapper;

    public SafetynetApiRepository() {
        mapper = new ObjectMapper();
    }

    public JsonNode extractNodes() throws IOException {
//        mapper = new ObjectMapper();
//
//        File file = new File("safetynetApi/src/main/resources"+resourceLink);
        File file = new File(resourceLink);
        file.mkdirs();
        InputStream inputStream = TypeReference.class.getResourceAsStream(resourceLink);
        return mapper.readTree(file);
    }

    public void writeValuesInFile(JsonNode nodes) throws IOException {

//        File resultFile = new File("safetynetApi/src/main/resources"+resourceLink);
        File resultFile = new File(resourceLink);
        resultFile.mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream(resultFile);
        mapper.writeValue(resultFile, nodes);

        fileOutputStream.close();
    }

}
