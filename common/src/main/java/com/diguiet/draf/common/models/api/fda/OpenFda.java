package com.diguiet.draf.common.models.api.fda;

import lombok.Data;

import java.util.ArrayList;

@Data
public class OpenFda {
    private ArrayList<String> application_number;
    private ArrayList<String> brand_name;
    private ArrayList<String> generic_name;
    private ArrayList<String> manufacturer_name;
    private ArrayList<String> product_ndc;
    private ArrayList<String> product_type;
    private ArrayList<String> route;
    private ArrayList<String> substance_name;
    private ArrayList<String> rxcui;
    private ArrayList<String> spl_id;
    private ArrayList<String> spl_set_id;
    private ArrayList<String> package_ndc;
    private ArrayList<String> unii;
}
