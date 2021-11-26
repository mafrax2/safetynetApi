package com.openclassrooms.safetynetApi.unitTest;


import com.openclassrooms.safetynetApi.controller.SafetyNetController;
import com.openclassrooms.safetynetApi.service.SafetyAppService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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

}
