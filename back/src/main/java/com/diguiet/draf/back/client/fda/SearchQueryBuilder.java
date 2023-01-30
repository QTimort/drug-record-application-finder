package com.diguiet.draf.back.client.fda;

import lombok.NonNull;
import okhttp3.HttpUrl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private final Map<String, SearchQueryBuilder.SearchField> searchFields = new HashMap<>();
    private int limit;
    private int page;


    private record SearchField(
            String name,
            boolean exact
    ) {}

    public SearchQueryBuilder(@NonNull final HttpUrl endpoint, final int maxLimit) {
        if (maxLimit < 0) {
            throw new IllegalArgumentException("maxLimit must be zero or a positive integer");
        }
        this.urlBuilder = endpoint.newBuilder();
        this.maxLimit = maxLimit;
        this.limit = maxLimit;
        this.page = 1;
    }

    public SearchQueryBuilder(@NonNull final String endpoint, final int maxLimit) {
        this(Objects.requireNonNull(HttpUrl.parse(endpoint)), maxLimit);
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
                Integer.toString(getSkipPagination())
        ).build();
    }

    public SearchQueryBuilder withLimit(final int limit) {
        if (limit > this.maxLimit) {
            throw new IllegalArgumentException("Limit must be lower than " + this.maxLimit);
        }
        if (limit < 0) {
            throw new IllegalArgumentException("Limit must be zero or a positive integer");
        }
        this.limit = limit;
        return this;
    }

    public SearchQueryBuilder withPage(final int page) {
        if (page < 1) {
            throw new IllegalArgumentException("Page must a positive integer");
        }
        this.page = page;
        return this;
    }

    private SearchQueryBuilder setField(
            @NonNull final String fieldName,
            @NonNull final String manufacturerName,
            final boolean exactMatch
    ) {
        this.searchFields.put(fieldName, new SearchQueryBuilder.SearchField(manufacturerName, exactMatch));
        return this;
    }

    public SearchQueryBuilder setManufacturerName(@NonNull final String manufacturerName, final boolean exactMatch) {
        return setField(SearchQueryBuilder.MANUFACTURER_NAME_FIELD, manufacturerName, exactMatch);
    }

    public SearchQueryBuilder setBrandNameName(@NonNull final String brandName, final boolean exactMatch) {
        return setField(SearchQueryBuilder.BRAND_NAME_FIELD, brandName, exactMatch);
    }

    private int getSkipPagination() {
        return (this.page - 1) * this.limit;
    }
    private static String searchFieldQueryToString(
            @NonNull final String fieldName,
            @NonNull final SearchQueryBuilder.SearchField searchField
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