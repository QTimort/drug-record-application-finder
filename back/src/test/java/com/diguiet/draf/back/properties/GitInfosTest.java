package com.diguiet.draf.back.properties;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitInfosTest {

    private final GitInfos gitInfos;

    public GitInfosTest(@Autowired @NonNull final GitInfos gitInfos) {
        this.gitInfos = gitInfos;
    }

    @Test
    void testGitInfosPresent() {
        Assertions.assertNotNull(this.gitInfos.getBackVersion());
        Assertions.assertNotNull(this.gitInfos.getBackVersion().buildVersion());
    }
}
