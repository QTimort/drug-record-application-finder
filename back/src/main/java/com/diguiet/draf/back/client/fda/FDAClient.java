package com.diguiet.draf.back.client.fda;

import com.diguiet.draf.common.models.api.fda.DrugsFdaResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Getter
@AllArgsConstructor
public class FDAClient {

    private final RestTemplate restTemplate;
    private final String fdaDrugEndpoint;
    private final int maxPageSize;

    public FDAClient(@NonNull final String fdaDrugEndpoint, final int maxPageSize) {
        this(new RestTemplate(), fdaDrugEndpoint, maxPageSize);
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
