package com.diguiet.draf.back.services.fda;

import com.diguiet.draf.common.models.fda.DrugsFdaResponse;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import okhttp3.HttpUrl;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FDAQueryService {
    @Getter
    private final HttpUrl fdaDrugEndpoint;
    private final RestTemplate restTemplate;
    private final int maxPageSize;

    public FDAQueryService(
            @Value("${fda.api.json}") @NonNull final String fdaDrugEndpoint,
            @Value("${fda.api.page.maxSize}") final int maxPageSize
    ) {
        this.fdaDrugEndpoint = HttpUrl.parse(fdaDrugEndpoint);
        this.maxPageSize = maxPageSize;
        this.restTemplate = new RestTemplate();
    }

    public DrugsFdaResponse getByManufacturer(
            @NonNull final String manufacturer
    ) {
        final URI queryUrl = this.newSearchQueryBuilder()
                .setManufacturerName(manufacturer, false)
                .build()
                .uri();

        return this.restTemplate.getForObject(
                queryUrl,
                DrugsFdaResponse.class
        );
    }

    public DrugsFdaResponse getByManufacturerBrand(
            @NonNull final String manufacturer,
            @NonNull final String brand
    ) {
        final URI queryUrl = this.newSearchQueryBuilder()
                .setManufacturerName(manufacturer, false)
                .setBrandNameName(brand, true)
                .build()
                .uri();

        return this.restTemplate.getForObject(
                queryUrl,
                DrugsFdaResponse.class
        );
    }

    public SearchQueryBuilder newSearchQueryBuilder() {
        return new SearchQueryBuilder();
    }

    public class SearchQueryBuilder {
        private static final String SEARCH_QUERY_PARAMETER_NAME = "search";
        private static final String LIMIT_QUERY_PARAMETER_NAME = "limit";
        private static final String SKIP_QUERY_PARAMETER_NAME = "skip";
        private static final String MANUFACTURER_NAME_FIELD = "openfda.manufacturer_name";
        private static final String BRAND_NAME_FIELD = "openfda.brand_name";
        private static final String EXACT_MATCH_SUFFIX = ".exact";
        private static final String AND_JOIN_OPERATOR = "\"+AND+\"";
        private final HttpUrl.Builder urlBuilder;
        private final int maxLimit;

        private final Map<String, SearchField> searchFields = new HashMap<>();
        private int limit;
        private int skip;


        private record SearchField(
                String name,
                boolean exact
        ) {}

        public SearchQueryBuilder(HttpUrl.Builder urlBuilder, int maxLimit) {
            this.urlBuilder = urlBuilder;
            this.maxLimit = maxLimit;
            this.limit = maxLimit;
            this.skip = 0;
        }

        private SearchQueryBuilder() {
            this(FDAQueryService.this.fdaDrugEndpoint.newBuilder(), FDAQueryService.this.maxPageSize);
        }

        public HttpUrl build() {
            final String searchQueryParameterValue = this.searchFields.entrySet()
                    .stream()
                    .map(searchField -> searchFieldQueryToString(searchField.getKey(), searchField.getValue()))
                    .collect(Collectors.joining(SearchQueryBuilder.AND_JOIN_OPERATOR));

            return this.urlBuilder.setQueryParameter(
                    SearchQueryBuilder.SEARCH_QUERY_PARAMETER_NAME,
                    searchQueryParameterValue
            ).setQueryParameter(
                    SearchQueryBuilder.LIMIT_QUERY_PARAMETER_NAME,
                    Integer.toString(this.limit)
            ).setQueryParameter(
                    SearchQueryBuilder.SKIP_QUERY_PARAMETER_NAME,
                    Integer.toString(this.skip)
            ).build();
        }

        public SearchQueryBuilder withLimit(final int limit) {
            if (limit > this.maxLimit) {
                throw new IllegalArgumentException("Provided Limit exceed max allowed limit of " + this.maxLimit);
            }
            if (limit < 0) {
                throw new IllegalArgumentException("Provided Limit must be a positive integer");
            }
            this.limit = limit;
            return this;
        }

        public SearchQueryBuilder withSkip(final int skip) {
            if (skip < 0) {
                throw new IllegalArgumentException("Provided skip must be a positive integer");
            }
            this.skip = skip;
            return this;
        }

        private SearchQueryBuilder setField(
                @NonNull final String fieldName,
                @NonNull final String manufacturerName,
                final boolean exactMatch
        ) {
            this.searchFields.put(fieldName, new SearchField(manufacturerName, exactMatch));
            return this;
        }

        public SearchQueryBuilder setManufacturerName(@NonNull final String manufacturerName, final boolean exactMatch) {
            return setField(SearchQueryBuilder.MANUFACTURER_NAME_FIELD, manufacturerName, exactMatch);
        }

        public SearchQueryBuilder setBrandNameName(@NonNull final String brandName, final boolean exactMatch) {
            return setField(SearchQueryBuilder.BRAND_NAME_FIELD, brandName, exactMatch);
        }

        private static String searchFieldQueryToString(
                @NonNull final String fieldName,
                @NonNull final FDAQueryService.SearchQueryBuilder.SearchField searchField
        ) {
            final StringBuilder stringBuilder = new StringBuilder(fieldName);
            if (searchField.exact) {
                stringBuilder.append(SearchQueryBuilder.EXACT_MATCH_SUFFIX);
            }
            return stringBuilder.append(':').append(searchField.name).toString();
        }
    }

}
