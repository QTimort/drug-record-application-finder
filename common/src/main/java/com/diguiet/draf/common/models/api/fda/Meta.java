package com.diguiet.draf.common.models.api.fda;

import lombok.Data;

@Data
public class Meta {
    private String disclaimer;
    private String terms;
    private String license;
    private String last_updated;
    private Results results;
}
