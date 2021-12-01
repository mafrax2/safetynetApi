package com.openclassrooms.safetynetApi.unitTest;


import com.openclassrooms.safetynetApi.controller.SafetyNetController;
import com.openclassrooms.safetynetApi.service.SafetyAppService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SafetyNetController.class)
public class SafetyNetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SafetyAppService safetyAppService;

    @Test
    public void firestationPeopleTest() throws Exception{
        mockMvc.perform(get("/firestation?firestationNumber=1")).andExpect(status().isOk());
    }

    @Test
    public void listTest() throws Exception{
        mockMvc.perform(get("/persons")).andExpect(status().isOk());
    }

    @Test
    public void phoneAlertTest() throws Exception {
        mockMvc.perform(get("/phoneAlert?firestation=1")).andExpect(status().isOk());
    }

    @Test
    public void childAlertTest() throws Exception {
        mockMvc.perform(get("/childAlert?address=adress")).andExpect(status().isOk());
    }

    @Test
    public void fireTest() throws Exception {
        mockMvc.perform(get("/fire?address=adress")).andExpect(status().isOk());
    }

    @Test
    public void floodTest() throws Exception {
        mockMvc.perform(get("/flood?stations=1,2")).andExpect(status().isOk());
    }

    @Test
    public void personInfoTest() throws Exception {
        mockMvc.perform(get("/personInfo?lastName=lastname&firstName=firstname")).andExpect(status().isOk());
    }

}
