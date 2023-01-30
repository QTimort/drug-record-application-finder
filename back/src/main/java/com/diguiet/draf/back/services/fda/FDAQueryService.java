package com.diguiet.draf.back.services.fda;

import com.diguiet.draf.common.models.fda.DrugsFdaResponse;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import okhttp3.HttpUrl;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
        private static final String AND_JOIN_OPERATOR = "+AND+";
        private final HttpUrl.Builder urlBuilder;
        private final int maxLimit;

        private final Map<String, SearchField> searchFields = new HashMap<>();
        private int limit;
        private int page;


        private record SearchField(
                String name,
                boolean exact
        ) {}

        public SearchQueryBuilder(@NonNull final HttpUrl.Builder urlBuilder, final int maxLimit) {
            this.urlBuilder = urlBuilder;
            this.maxLimit = maxLimit;
            this.limit = maxLimit;
            this.page = 1;
        }

        private SearchQueryBuilder() {
            this(FDAQueryService.this.fdaDrugEndpoint.newBuilder(), FDAQueryService.this.maxPageSize);
        }

        public HttpUrl build() {
            final String searchQueryParameterValue = this.searchFields.entrySet()
                    .stream()
                    .map(searchField -> searchFieldQueryToString(searchField.getKey(), searchField.getValue()))
                    .collect(Collectors.joining(SearchQueryBuilder.AND_JOIN_OPERATOR));

            return this.urlBuilder.setEncodedQueryParameter(
                    SearchQueryBuilder.SEARCH_QUERY_PARAMETER_NAME,
                    searchQueryParameterValue
            ).setQueryParameter(
                    SearchQueryBuilder.LIMIT_QUERY_PARAMETER_NAME,
                    Integer.toString(this.limit)
            ).setQueryParameter(
                    SearchQueryBuilder.SKIP_QUERY_PARAMETER_NAME,
                    Integer.toString((this.page - 1) * this.limit)
            ).build();
        }

        public SearchQueryBuilder withLimit(final int limit) {
            if (limit > this.maxLimit) {
                throw new IllegalArgumentException("Provided Limit exceed max allowed limit of " + this.maxLimit);
            }
            if (limit < 0) {
                throw new IllegalArgumentException("Provided Limit must be zero or a positive integer");
            }
            this.limit = limit;
            return this;
        }

        public SearchQueryBuilder withPage(final int page) {
            if (page < 1) {
                throw new IllegalArgumentException("Provided skip must be a positive integer");
            }
            this.page = page;
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
            return stringBuilder
                    .append(':')
                    .append("\"")
                    .append(URLEncoder.encode(searchField.name, StandardCharsets.UTF_8))
                    .append("\"")
                    .toString();
        }
    }

}
