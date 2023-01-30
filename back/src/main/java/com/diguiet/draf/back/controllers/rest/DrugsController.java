package com.diguiet.draf.back.controllers.rest;

import com.diguiet.draf.back.services.fda.FDAQueryService;
import com.diguiet.draf.common.models.fda.DrugsFdaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.function.Supplier;

@RestController
@RequestMapping("/drugs/")
@Slf4j
public class DrugsController {
    private final FDAQueryService fdaQueryService;

    public DrugsController(@Autowired @NonNull final FDAQueryService fdaQueryService) {
        this.fdaQueryService = fdaQueryService;
    }

    @Operation(summary = "Get Manufacturer Drugs")
    @Parameters(value = {
            @Parameter(
                    name = "manufacturer",
                    example = "Hospira, Inc.",
                    description = "The Manufacturer to query",
                    schema = @Schema(implementation = String.class)
            )
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get the drugs related to a manufacturer",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DrugsFdaResponse.class)) }),
    })
    @CrossOrigin
    @GetMapping("/manufacturers/{manufacturer}")
    public DrugsFdaResponse getManufacturerDrugs(
            @PathVariable @NonNull final String manufacturer,
            @RequestParam(required = true, defaultValue = "1") final int page
    ) {
        return handleNotFoundException(() -> this.fdaQueryService.getByManufacturer(manufacturer, page));
    }

    @Operation(summary = "Get Manufacturer Brand Drugs")
    @Parameters(value = {
            @Parameter(
                    name = "manufacturer",
                    example = "Hospira, Inc.",
                    description = "The Manufacturer to query",
                    schema = @Schema(implementation = String.class)
            ),
            @Parameter(
                    name = "brand",
                    example = "HEPARIN SODIUM",
                    description = "The brand to query",
                    schema = @Schema(implementation = String.class)
            )
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get the drugs related to a manufacturer and brand",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DrugsFdaResponse.class)) }),
    })
    @CrossOrigin
    @GetMapping("/manufacturers/{manufacturer}/brands/{brand}")
    public DrugsFdaResponse getManufacturerBrandDrugs(
            @PathVariable @NonNull final String manufacturer,
            @PathVariable @NonNull final String brand,
            @RequestParam(required = true, defaultValue = "1") @Nullable final int page
    ) {
        return handleNotFoundException(() -> this.fdaQueryService.getByManufacturerBrand(manufacturer, brand, page));
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
