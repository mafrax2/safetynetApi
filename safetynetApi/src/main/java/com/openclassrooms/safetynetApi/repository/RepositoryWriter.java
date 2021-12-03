package com.openclassrooms.safetynetApi.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Data
@AllArgsConstructor
public class RepositoryWriter {

    @Value("${json.path:}")
    private String resourceLink;
    private ObjectMapper mapper;

    public RepositoryWriter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public JsonNode extractNodes() throws IOException {
        mapper = new ObjectMapper();
        File file = new File("safetynetApi/src/main/resources/json/data.json");
        return mapper.readTree(file);
    }

    public void writeValuesInFile(JsonNode nodes) throws IOException {

        File resultFile = new File(resourceLink);
        FileOutputStream fileOutputStream = new FileOutputStream(resultFile);
        resultFile.mkdirs();
        mapper.writeValue(resultFile, nodes);

        fileOutputStream.close();
    }


}
