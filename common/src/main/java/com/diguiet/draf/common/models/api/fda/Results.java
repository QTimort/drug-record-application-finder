package com.diguiet.draf.common.models.api.fda;

import lombok.Data;

@Data
public class Results {
    private int skip;
    private int limit;
    private int total;
}
