package com.diguiet.draf.back.rest;


import com.diguiet.draf.back.controllers.rest.DrugsController;
import lombok.NonNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
        this.mockMvc
                .perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.results.total").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.results.products[0].brand_name").exists());
    }
}