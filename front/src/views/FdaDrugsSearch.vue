<template>
  <v-container>
    <h1>Search</h1>
    <v-responsive class="d-flex align-center text-center fill-height">
      <v-row class="d-flex align-center justify-center">
        <v-col>
          <v-text-field
            id="manufacturer-input"
            v-model="parameters.manufacturerName"
            :disabled="query.loading"
            label="FDA Manufacturer Name"
            placeholder="Hospira, Inc."
          />
        </v-col>
        <v-col>
          <v-text-field
            id="brand-input"
            v-model="parameters.brandName"
            :disabled="query.loading"
            label="FDA Brand Name (Optional)"
            placeholder="Heparin Sodium"
          />
        </v-col>
        <v-col style="display: flex">
          <v-btn
            id="search-btn"
            :loading="query.loading"
            :disabled="searchDisabled"
            color="indigo"
            @click="store.search()"
          >
            Search
          </v-btn>
        </v-col>
      </v-row>
      <v-container>
        <v-alert v-if="query.error" type="error">
          An error occurred while fetching the data. Please check your
          connection with the server and your research parameters.
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
              <tr
                v-for="result in query.results?.results || []"
                :key="result?.application_number"
              >
                <td class="text-left">
                  {{ result?.openfda?.manufacturer_name?.[0] || '?' }}
                </td>
                <td class="text-left">
                  {{ result?.openfda?.brand_name?.[0] || '?' }}
                </td>
                <td class="text-left">
                  {{ result?.openfda?.product_ndc?.join?.(', ') || '?' }}
                </td>
              </tr>
            </tbody>
          </v-table>
        </v-container>
      </v-row>
      <div v-if="numberOfPages !== null || query.loading" class="text-center">
        <v-pagination
          :model-value="parameters.page"
          :length="numberOfPages"
          :disabled="query.loading"
          :total-visible="7"
          @update:model-value="store.search"
        />
      </div>
      <p v-if="resultsDisplayed" id="result-count">
        Displaying {{ resultsDisplayed }} out of {{ query.results?.meta.results.total }} result(s)
      </p>
    </v-responsive>
  </v-container>
</template>

<script lang="ts" setup>
import { storeToRefs } from 'pinia';
import { useFdaDrugSearchStore } from '@/stores/fdaDrugSearch';

const store = useFdaDrugSearchStore();
const { query, parameters, searchDisabled, numberOfPages, resultsDisplayed } = storeToRefs(store);
</script>
