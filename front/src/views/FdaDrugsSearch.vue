<template>
  <v-container>
    <h1>Search</h1>
    <v-responsive class="d-flex align-center text-center fill-height">
      <v-row class="d-flex align-center justify-center">
        <v-col>
          <v-text-field :disabled="loading" v-model="manufacturerName" label="FDA Manufacturer Name" placeholder="Hospira, Inc." />
        </v-col>
        <v-col>
          <v-text-field :disabled="loading" v-model="brandName" label="FDA Brand Name (Optional)" placeholder="Heparin Sodium" />
        </v-col>
        <v-col style="display: flex;">
          <v-btn :loading="loading" :disabled="searchDisabled()" @click="search" color="indigo" >
            Search
          </v-btn>
        </v-col>
      </v-row>
      <v-container>
        <v-alert v-if="error" type="error">
          An error occurred while fetching the data.
          Please check your connection with the server and your research parameters.
        </v-alert>
      </v-container>
      <v-row class="d-flex align-center justify-center">
        <v-container fluid>
          <v-table fixed-header height="50vh" class="">
            <thead>
            <tr>
              <th class="text-left">Manufacturer Name</th>
              <th class="text-left">FDA Brand Name</th>
              <th class="text-left">Product(s)</th>
            </tr>
            </thead>
            <tbody>
            <tr v v-for="result in (results?.results || [])" :key="result?.application_number">
              <td class="text-left">{{ result?.openfda?.manufacturer_name?.[0] || '?' }}</td>
              <td class="text-left">{{ result?.openfda?.brand_name?.[0] || '?' }}</td>
              <td class="text-left">{{ result?.products.map(p => p.brand_name).join(', ') || '?' }}</td>
            </tr>
            </tbody>
          </v-table>
        </v-container>
      </v-row>
      <p>{{results?.meta?.results?.total || 0}} result(s)</p>
    </v-responsive>
  </v-container>
</template>

<script lang="ts" setup>
import {ref} from "vue";
import type {DrugsFdaResponse} from "@/types/drafModels";
import {getDrugsManufacturer, getDrugsManufacturerBrand} from "@/api/v1/drugs/fdaDrugs";
import {storeToRefs} from "pinia";
import {useAppStore} from "@/stores/app";

const { apiEndpoint } = storeToRefs(useAppStore());
const manufacturerName = ref<string>('')
const brandName = ref<string>('')
const loading = ref<boolean>(false);
const error = ref<boolean>(false);
const results = ref<DrugsFdaResponse | null>(null);

function searchDisabled() {
  return manufacturerName.value.length < 2 || loading.value;
}

function search() {
  loading.value = true;
  results.value = null;
  ((brandName.value.length === 0) ?
    getDrugsManufacturer(apiEndpoint.value, manufacturerName.value) :
    getDrugsManufacturerBrand(apiEndpoint.value, manufacturerName.value, brandName.value))
    .then(response => {
      results.value = response;
      loading.value = false;
      error.value = false;
    }).catch(ignored => {
      loading.value = false;
      error.value = true;
  })
}
</script>
