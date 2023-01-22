package com.diguiet.draf.back.services;

import com.diguiet.draf.common.models.fda.DrugsFdaResponse;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class FDADrugService {
    private static final String DEFAULT_FDA_DRUG_API_ENDPOINT = "https://api.fda.gov/drug/drugsfda.json";

    @Getter
    private final String fdaDrugEndpoint;
    private final RestTemplate restTemplate;

    public FDADrugService(@NonNull final String fdaDrugEndpoint) {
        this.fdaDrugEndpoint = fdaDrugEndpoint;
        this.restTemplate = new RestTemplate();
    }

    public FDADrugService() {
        this(FDADrugService.DEFAULT_FDA_DRUG_API_ENDPOINT);
    }

    public DrugsFdaResponse getByManufacturer(
            @NonNull final String manufacturer
    ) {
        return this.restTemplate.getForObject(
                this.fdaDrugEndpoint + "?search=openfda.manufacturer_name.exact:\"{manufacturer}\"&limit=1000",
                DrugsFdaResponse.class,
                manufacturer
        );
    }

    public DrugsFdaResponse getByManufacturerBrand(
            @NonNull final String manufacturer,
            @NonNull final String brand
    ) {
        return this.restTemplate.getForObject(
                this.fdaDrugEndpoint +
                        "?search=openfda.manufacturer_name.exact:\"{manufacturer}\"," +
                        "openfda.brand_name.exact:\"{brand}\"&limit=1000",
                DrugsFdaResponse.class,
                manufacturer,
                brand
        );
    }

}
