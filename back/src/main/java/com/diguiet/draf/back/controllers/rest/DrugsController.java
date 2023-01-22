package com.diguiet.draf.back.controllers.rest;

import com.diguiet.draf.common.models.Drug;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/drugs/")
public class DrugsController {


    @Operation(summary = "Get Manufacturer Drugs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get the drugs related to a manufacturer",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Drug.class)) }),
    })
    @CrossOrigin
    @GetMapping("/manufacturers/{manufacturer}")
    public List<Drug> getManufacturerDrugs(
            @PathVariable @NonNull final String manufacturer
    ) {
        // todo implement
        return new ArrayList<>();
    }

    @Operation(summary = "Get Manufacturer Brand Drugs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get the drugs related to a manufacturer and brand",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Drug.class)) }),
    })
    @CrossOrigin
    @GetMapping("/manufacturers/{manufacturer}/brands/{brand}")
    public List<Drug> getManufacturerBrandDrugs(
            @PathVariable @NonNull final String manufacturer,
            @PathVariable @NonNull final String brand
    ) {
        // todo implement
        return new ArrayList<>();
    }
}
