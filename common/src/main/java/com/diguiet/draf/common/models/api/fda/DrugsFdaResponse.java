package com.diguiet.draf.common.models.api.fda;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DrugsFdaResponse {
    private Meta meta;
    private ArrayList<Result> results;
}
