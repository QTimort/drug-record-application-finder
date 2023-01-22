package com.diguiet.draf.back.controllers.rest;

import com.diguiet.draf.back.properties.GitInfos;
import com.diguiet.draf.common.models.BackVersion;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    private final GitInfos gitInfos;

    public VersionController(@Autowired @NonNull final GitInfos gitInfos) {
        this.gitInfos = gitInfos;
    }

    @GetMapping("version")
    public BackVersion getVersion() {
        return this.gitInfos.getBackVersion();
    }
}
