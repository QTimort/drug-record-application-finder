package com.diguiet.draf.back.controllers.rest;

import com.diguiet.draf.back.services.FDADrugService;
import com.diguiet.draf.common.models.fda.DrugsFdaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.function.Supplier;

@RestController
@RequestMapping("/drugs/")
@Slf4j
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
        return handleNotFoundException(() -> this.fdaDrugService.getByManufacturer(manufacturer));
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
        return handleNotFoundException(() -> this.fdaDrugService.getByManufacturerBrand(manufacturer, brand));
    }

    private static DrugsFdaResponse handleNotFoundException(@NonNull final Supplier<DrugsFdaResponse> supplier) {
        try {
            return supplier.get();
        } catch (HttpClientErrorException.NotFound e) {
            log.debug("DrugsFdaResponse supplier raised Not Found exception", e);
            return new DrugsFdaResponse();
        }
    }
}
