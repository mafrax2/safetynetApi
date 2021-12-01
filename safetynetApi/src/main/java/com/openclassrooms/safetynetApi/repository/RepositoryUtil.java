package com.openclassrooms.safetynetApi.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Data
@AllArgsConstructor
public class RepositoryUtil {


    private static ObjectMapper mapper;

    public static void writeValuesInFile(JsonNode nodes) throws IOException {

        File resultFile = new File("safetynetApi/src/main/resources/json/data.json");
        FileOutputStream fileOutputStream = new FileOutputStream(resultFile);
        resultFile.mkdirs();
        mapper.writeValue(resultFile, nodes);

        fileOutputStream.close();
    }

    public static JsonNode extractNodes() throws IOException {
        mapper = new ObjectMapper();
        File file = new File("safetynetApi/src/main/resources/json/data.json");
        return mapper.readTree(file);
    }

}
