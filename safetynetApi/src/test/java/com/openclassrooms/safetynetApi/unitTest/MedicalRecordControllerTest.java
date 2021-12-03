package com.openclassrooms.safetynetApi.unitTest;

import com.openclassrooms.safetynetApi.controller.MedicalRecordController;
import com.openclassrooms.safetynetApi.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Test
    public void addMedicalRecordTest() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .post("/medicalrecord")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Marc\",\"lastName\":\"MARC\",\"birthdate\":\"26/12/1990\",\"medications\":[], \"allergies\":[]}")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    public void editMedicalRecordTest() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .put("/medicalrecord")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Marc\",\"lastName\":\"MARC\",\"birthdate\":\"26/12/1990\",\"medications\":[\"truc\"], \"allergies\":[\"truc\"]}")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void deleteMedicalRecordTest() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/medicalrecord?firstName=marc&lastName=marc");
        mockMvc.perform(request).andExpect(status().isOk());
    }

}
