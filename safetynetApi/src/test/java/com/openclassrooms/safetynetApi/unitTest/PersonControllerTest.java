package com.openclassrooms.safetynetApi.unitTest;


import com.openclassrooms.safetynetApi.controller.PersonController;
import com.openclassrooms.safetynetApi.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    public void firestationPeopleTest() throws Exception{
        mockMvc.perform(get("firestation?firestationNumber=1")).andExpect(status().isOk());
    }

}
