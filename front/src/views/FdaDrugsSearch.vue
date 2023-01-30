<template>
  <v-container>
    <h1>Search</h1>
    <v-responsive class="d-flex align-center text-center fill-height">
      <v-row class="d-flex align-center justify-center flex-column flex-sm-row">
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
        <v-col>
          <v-btn
            id="search-btn"
            :class="$vuetify.display.smAndDown ? 'w-100' : 'float-left'"
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
        <v-alert v-if="query.error !== null" type="error">
          {{ query.error?.error }}
        </v-alert>
      </v-container>
      <v-row class="d-flex align-center justify-center">
        <v-container fluid>
          <v-table fixed-header height="50vh">
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
                  <span
                    v-for="(ndc, index) in result?.openfda?.product_ndc || []"
                    :key="index"
                  >
                    <a
                      :href="'https://ndclist.com/ndc/' + ndc"
                      target="_blank"
                      rel="noopener noreferrer"
                    >
                      {{ ndc }}
                    </a>
                    <span
                      v-if="
                        index + 1 < (result?.openfda?.product_ndc?.length || 0)
                      "
                    >
                      ,
                    </span>
                  </span>
                </td>
              </tr>
            </tbody>
          </v-table>
        </v-container>
      </v-row>
      <div v-if="numberOfPages !== null || query.loading" class="text-center">
        <v-pagination
          :model-value="parameters.page"
          :length="numberOfPages || 0"
          :disabled="query.loading"
          :total-visible="7"
          @update:model-value="store.search"
        />
      </div>
      <p v-if="resultsDisplayed !== null" id="result-count">
        Displaying {{ resultsDisplayed }} out of
        {{ query.results?.meta.results.total }} result(s)
      </p>
    </v-responsive>
  </v-container>
</template>

<script lang="ts" setup>
import { storeToRefs } from 'pinia';
import { useFdaDrugSearchStore } from '@/stores/fdaDrugSearch';

const store = useFdaDrugSearchStore();
const { query, parameters, searchDisabled, numberOfPages, resultsDisplayed } =
  storeToRefs(store);
</script>
