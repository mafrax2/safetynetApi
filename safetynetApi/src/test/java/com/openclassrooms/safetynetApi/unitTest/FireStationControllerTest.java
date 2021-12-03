package com.openclassrooms.safetynetApi.unitTest;

import com.openclassrooms.safetynetApi.controller.FireStationController;
import com.openclassrooms.safetynetApi.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    @Test
    public void addFireStationTest() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .post("/firestation")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"address\":\"here\", \"station\":2 }")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    public void editFireStationTest() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .put("/firestation")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"address\":\"here\", \"station\":2 }")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void deleteFireStationTest() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/firestation?station=2");
        mockMvc.perform(request).andExpect(status().isOk());
    }

}
