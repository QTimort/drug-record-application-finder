package com.diguiet.draf.back.controllers.rest;

import com.diguiet.draf.back.properties.GitProperties;
import com.diguiet.draf.common.models.BackVersion;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    private final GitProperties gitProperties;

    public VersionController(@Autowired @NonNull final GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @GetMapping("version")
    public BackVersion getVersion() {
        return this.gitProperties.getBackVersion();
    }
}
