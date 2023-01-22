package com.diguiet.draf.back.properties;

import com.diguiet.draf.common.annotations.Immutable;
import com.diguiet.draf.common.models.version.BackVersion;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@Getter
@Immutable
@PropertySource("classpath:git.properties")
public class GitInfos {
    private final BackVersion backVersion;

    public GitInfos(@Autowired @NonNull final Environment environment) {
        this.backVersion = BackVersion
                .builder()
                .branch(environment.getProperty("git.branch"))
                .buildHost(environment.getProperty("git.build.host"))
                .buildVersion(environment.getProperty("git.build.version"))
                .closestTag(environment.getProperty("git.closest.tag.nam"))
                .commitId(environment.getProperty("git.commit.id"))
                .commitMessage(environment.getProperty("git.commit.message.full"))
                .dirty(environment.getProperty("git.dirty"))
                .build();
    }
}
