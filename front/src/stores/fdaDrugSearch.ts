// Utilities
import { defineStore } from 'pinia';
import type { ApiError, DrugsFdaResponse } from '@/types/drafModels';
import {
  getDrugsManufacturer,
  getDrugsManufacturerBrand,
} from '@/api/v1/drugs/fdaDrugs';
import { useAppStore } from '@/stores/app';

export interface FdaDrugSearchState {
  parameters: {
    manufacturerName: string;
    brandName: string;
    page: number;
  };
  query: {
    loading: boolean;
    error: ApiError | null;
    results: DrugsFdaResponse | null;
  };
}

export const useFdaDrugSearchStore = defineStore('fdaDrugResearch', {
  state: (): FdaDrugSearchState => {
    return {
      parameters: {
        manufacturerName: '',
        brandName: '',
        page: 1,
      },
      query: {
        loading: false,
        error: null,
        results: null,
      },
    };
  },
  getters: {
    numberOfPages: state => {
      if (
        state.query.loading ||
        state.query.error ||
        !state.query.results?.meta?.results
      ) {
        return null;
      }
      const { total, limit } = state.query.results.meta.results;
      return Math.ceil(total / limit);
    },
    resultsDisplayed: state => {
      if (
        state.query.loading ||
        state.query.error ||
        !state.query.results?.meta?.results
      ) {
        return null;
      }
      const { total, limit, skip } = state.query.results.meta.results;
      return limit + skip < total ? limit : total % limit;
    },
    searchDisabled: state =>
      state.parameters.manufacturerName.length < 2 || state.query.loading,
  },
  actions: {
    search(pageToSearch = 1) {
      const { apiEndpoint } = useAppStore();
      this.parameters.page = pageToSearch;
      this.query = {
        loading: true,
        error: null,
        results: null,
      };
      (this.parameters.brandName.length === 0
        ? getDrugsManufacturer(
            apiEndpoint,
            this.parameters.manufacturerName,
            pageToSearch
          )
        : getDrugsManufacturerBrand(
            apiEndpoint,
            this.parameters.manufacturerName,
            this.parameters.brandName,
            pageToSearch
          )
      )
        .then(response => {
          this.query = {
            loading: false,
            error: null,
            results: response,
          };
        })
        .catch(error => {
          this.query = {
            loading: false,
            error: { error: error?.body?.error || 'An unknown error occurred' },
            results: null,
          };
        });
    },
  },
});
