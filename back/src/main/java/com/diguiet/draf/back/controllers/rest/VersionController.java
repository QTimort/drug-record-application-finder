package com.diguiet.draf.back.controllers.rest;

import com.diguiet.draf.back.properties.GitInfos;
import com.diguiet.draf.common.models.BackVersion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    private final GitInfos gitInfos;

    public VersionController(@Autowired @NonNull final GitInfos gitInfos) {
        this.gitInfos = gitInfos;
    }

    @Operation(summary = "Get Version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get the  server version",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BackVersion.class)) }),
    })
    @CrossOrigin
    @GetMapping("/version")
    public BackVersion getVersion() {
        return this.gitInfos.getBackVersion();
    }
}
