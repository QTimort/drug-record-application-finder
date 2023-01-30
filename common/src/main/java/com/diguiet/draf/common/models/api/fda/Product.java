package com.diguiet.draf.common.models.api.fda;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Product {
    private String product_number;
    private String reference_drug;
    private String brand_name;
    private ArrayList<ActiveIngredient> active_ingredients;
    private String reference_standard;
    private String dosage_form;
    private String route;
    private String marketing_status;
}
