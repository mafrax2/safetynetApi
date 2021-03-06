package com.openclassrooms.safetynetApi.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Data
@AllArgsConstructor
@Repository
@Log4j2
public class SafetynetApiRepository {

    @Value("${json.path:}")
    private String resourceLink;
    private ObjectMapper mapper;

    public SafetynetApiRepository() {
        mapper = new ObjectMapper();
    }

    public JsonNode extractNodes() throws IOException {
        File file = new File(resourceLink);
        file.mkdirs();
        JsonNode jsonNode = mapper.readTree(file);

        return jsonNode;
    }

    public void writeValuesInFile(JsonNode nodes) throws IOException {

        File resultFile = new File(resourceLink);
        resultFile.mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream(resultFile);
        mapper.writeValue(resultFile, nodes);

        fileOutputStream.close();
    }

}
