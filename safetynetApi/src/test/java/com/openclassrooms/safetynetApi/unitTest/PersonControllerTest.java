package com.openclassrooms.safetynetApi.unitTest;

import com.openclassrooms.safetynetApi.controller.PersonController;
import com.openclassrooms.safetynetApi.controller.SafetyNetController;
import com.openclassrooms.safetynetApi.service.PersonService;
import com.openclassrooms.safetynetApi.service.SafetyAppService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    public void addPersonTest() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .post("/person")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Marc\",\"lastName\":\"MARC\",\"address\":\"here\",\"city\":\"city\", \"zip\":2, \"phone\":\"phone\", \"email\":\"email\" }")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    public void editPersonTest() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .put("/person")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Marc\",\"lastName\":\"MARC\",\"address\":\"here\",\"city\":\"city\", \"zip\":2, \"phone\":\"phone\", \"email\":\"email\" }")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void deletePersonTest() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/person?firstName=marc&lastName=marc");
        mockMvc.perform(request).andExpect(status().isOk());
    }

}
