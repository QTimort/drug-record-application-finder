package com.diguiet.draf.back.services.fda;

import com.diguiet.draf.back.client.fda.FDAClient;
import com.diguiet.draf.common.models.api.fda.DrugsFdaResponse;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FDAQueryService {
    @Getter
    private final FDAClient fdaClient;

    public FDAQueryService(
            @Value("${fda.api.json}") @NonNull final String fdaDrugEndpoint,
            @Value("${fda.api.page.maxSize}") final int maxPageSize
    ) {
        this.fdaClient = new FDAClient(fdaDrugEndpoint, maxPageSize);
    }

    public DrugsFdaResponse getByManufacturer(
            @NonNull final String manufacturer,
            final int page
    ) {
        return this.fdaClient.getByManufacturer(manufacturer, page);
    }

    public DrugsFdaResponse getByManufacturerBrand(
            @NonNull final String manufacturer,
            @NonNull final String brand,
            final int page
    ) {
        return this.fdaClient.getByManufacturerBrand(manufacturer, brand, page);
    }

}
