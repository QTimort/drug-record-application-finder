/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.1.1185 on 2023-01-22 20:28:52.

export interface ApiError {
  error: string;
}

export interface ActiveIngredient {
  name: string;
  strength: string;
}

export interface DrugsFdaResponse {
  meta: Meta;
  results: Result[];
}

export interface Meta {
  disclaimer: string;
  terms: string;
  license: string;
  last_updated: string;
  results: Results;
}

export interface OpenFda {
  application_number: string[];
  brand_name: string[];
  generic_name: string[];
  manufacturer_name: string[];
  product_ndc: string[];
  product_type: string[];
  route: string[];
  substance_name: string[];
  rxcui: string[];
  spl_id: string[];
  spl_set_id: string[];
  package_ndc: string[];
  unii: string[];
}

export interface Product {
  product_number: string;
  reference_drug: string;
  brand_name: string;
  active_ingredients: ActiveIngredient[];
  reference_standard: string;
  dosage_form: string;
  route: string;
  marketing_status: string;
}

export interface Result {
  submissions: Submission[];
  application_number: string;
  sponsor_name: string;
  openfda: OpenFda;
  products: Product[];
}

export interface Results {
  skip: number;
  limit: number;
  total: number;
}

export interface Submission {
  submission_type: string;
  submission_number: string;
  submission_status: string;
  submission_status_date: string;
  submission_class_code: string;
  submission_class_code_description: string;
  review_priority: string;
}

export interface ServerVersion {
  branch: string;
  buildHost: string;
  buildVersion: string;
  closestTag: string;
  commitId: string;
  commitMessage: string;
  dirty: string;
}

export interface ServerVersionBuilder {}
