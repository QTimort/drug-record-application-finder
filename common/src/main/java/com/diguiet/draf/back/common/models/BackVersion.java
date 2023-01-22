package com.diguiet.draf.back.common.models;

import com.diguiet.draf.back.common.annotations.Immutable;
import lombok.Builder;

@Builder
@Immutable
public record BackVersion(
        String branch,
        String buildHost,
        String buildVersion,
        String closestTag,
        String commitId,
        String commitMessage,
        String dirty
) {
}