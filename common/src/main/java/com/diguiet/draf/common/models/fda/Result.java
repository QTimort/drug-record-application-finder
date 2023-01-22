package com.diguiet.draf.common.models.fda;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Result{
    private ArrayList<Submission> submissions;
    private String application_number;
    private String sponsor_name;
    private OpenFda openfda;
    private ArrayList<Product> products;
}

