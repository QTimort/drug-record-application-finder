package com.diguiet.draf.back.rest;


import com.diguiet.draf.back.controllers.rest.DrugsController;
import com.diguiet.draf.common.models.fda.DrugsFdaResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DrugsControllerTest {

    private final DrugsController drugsController;
    private final MockMvc mockMvc;

    public DrugsControllerTest(
            @Autowired @NonNull final DrugsController drugsController,
            @Autowired @NonNull final MockMvc mockMvc
    ) {
        this.drugsController = drugsController;
        this.mockMvc = mockMvc;
    }

    @Test
    public void contextLoads() {
        assertThat(this.drugsController).isNotNull();
    }

    @Test
    public void manufacturerDrugsShouldContainResult() throws Exception {
        testDrugsEndpoint("/drugs/manufacturers/Hospira, Inc.");
    }

    @Test
    public void manufacturerBrandDrugsShouldContainResult() throws Exception {
        testDrugsEndpoint("/drugs/manufacturers/Hospira, Inc./brands/HEPARIN SODIUM");
    }

    private void testDrugsEndpoint(String urlTemplate) throws Exception {
        final MvcResult mvcResult = this.mockMvc
                .perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        final String json = mvcResult.getResponse().getContentAsString();
        final DrugsFdaResponse parsedResponse = new ObjectMapper().readValue(json, DrugsFdaResponse.class);

        Assertions.assertNotNull(parsedResponse);
        Assertions.assertNotNull(parsedResponse.getResults());
        Assertions.assertNotNull(parsedResponse.getMeta());
        Assertions.assertEquals(
                parsedResponse.getMeta().getResults().getTotal(),
                parsedResponse.getResults().size()
        );
    }
}