package com.diguiet.draf.back.services.fda;

import com.diguiet.draf.back.client.fda.SearchQueryBuilder;
import com.diguiet.draf.common.models.fda.DrugsFdaResponse;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class FDAQueryService {
    @Getter
    private final String fdaDrugEndpoint;
    private final RestTemplate restTemplate;
    private final int maxPageSize;

    public FDAQueryService(
            @Value("${fda.api.json}") @NonNull final String fdaDrugEndpoint,
            @Value("${fda.api.page.maxSize}") final int maxPageSize
    ) {
        this.fdaDrugEndpoint = fdaDrugEndpoint;
        this.maxPageSize = maxPageSize;
        this.restTemplate = new RestTemplate();
    }

    public DrugsFdaResponse getByManufacturer(
            @NonNull final String manufacturer,
            final int page
    ) {
        final URI queryUrl = this.newSearchQueryBuilder()
                .setManufacturerName(manufacturer, false)
                .withPage(page)
                .build()
                .uri();

        return this.restTemplate.getForObject(
                queryUrl,
                DrugsFdaResponse.class
        );
    }

    public DrugsFdaResponse getByManufacturerBrand(
            @NonNull final String manufacturer,
            @NonNull final String brand,
            final int page
    ) {
        final URI queryUrl = this.newSearchQueryBuilder()
                .setManufacturerName(manufacturer, false)
                .setBrandNameName(brand, true)
                .withPage(page)
                .build()
                .uri();

        return this.restTemplate.getForObject(
                queryUrl,
                DrugsFdaResponse.class
        );
    }

    private SearchQueryBuilder newSearchQueryBuilder() {
        return new SearchQueryBuilder(this.fdaDrugEndpoint, this.maxPageSize);
    }

}
