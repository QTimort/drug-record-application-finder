package com.diguiet.draf.common.models.version;

import com.diguiet.draf.common.annotations.Immutable;
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