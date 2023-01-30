package com.diguiet.draf.back.rest;


import com.diguiet.draf.back.controllers.rest.VersionController;
import com.diguiet.draf.back.properties.GitInfos;
import lombok.NonNull;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VersionControllerTest {

    private final VersionController versionController;
    private final GitInfos gitInfos;
    private final MockMvc mockMvc;

    public VersionControllerTest(
            @Autowired @NonNull final VersionController versionController,
            @Autowired @NonNull final GitInfos gitInfos,
            @Autowired @NonNull final MockMvc mockMvc
    ) {
        this.versionController = versionController;
        this.gitInfos = gitInfos;
        this.mockMvc = mockMvc;
    }

    @Test
    public void contextLoads() {
        assertThat(this.versionController).isNotNull();
    }

    @Test
    public void shouldReturnBuildVersion() throws Exception {
        this.mockMvc
                .perform(get("/version"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.buildVersion",
                        StringContains.containsString(this.gitInfos.getServerVersion().buildVersion())
                ));
    }
}