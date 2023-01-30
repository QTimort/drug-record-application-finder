package com.diguiet.draf.common.models.api.error;

import com.diguiet.draf.common.annotations.Immutable;

@Immutable
public record ApiError (
        String error
) {}
