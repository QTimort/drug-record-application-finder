package com.diguiet.draf.back.controllers.rest;

import com.diguiet.draf.back.services.FDADrugService;
import com.diguiet.draf.common.models.fda.DrugsFdaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drugs/")
public class DrugsController {
    private final FDADrugService fdaDrugService;

    public DrugsController(@Autowired @NonNull final FDADrugService fdaDrugService) {
        this.fdaDrugService = fdaDrugService;
    }

    @Operation(summary = "Get Manufacturer Drugs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get the drugs related to a manufacturer",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DrugsFdaResponse.class)) }),
    })
    @CrossOrigin
    @GetMapping("/manufacturers/{manufacturer}")
    public DrugsFdaResponse getManufacturerDrugs(
            @PathVariable @NonNull final String manufacturer
    ) {
        DrugsFdaResponse byManufacturer = this.fdaDrugService.getByManufacturer(manufacturer);
        return byManufacturer;
    }

    @Operation(summary = "Get Manufacturer Brand Drugs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get the drugs related to a manufacturer and brand",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DrugsFdaResponse.class)) }),
    })
    @CrossOrigin
    @GetMapping("/manufacturers/{manufacturer}/brands/{brand}")
    public DrugsFdaResponse getManufacturerBrandDrugs(
            @PathVariable @NonNull final String manufacturer,
            @PathVariable @NonNull final String brand
    ) {
        return this.fdaDrugService.getByManufacturerBrand(manufacturer, brand);
    }
}
