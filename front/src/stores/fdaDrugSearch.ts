// Utilities
import {defineStore} from 'pinia';
import type {DrugsFdaResponse} from "@/types/drafModels";
import {getDrugsManufacturer, getDrugsManufacturerBrand} from "@/api/v1/drugs/fdaDrugs";
import {useAppStore} from "@/stores/app";

export interface FdaDrugSearchState {
  parameters: {
    manufacturerName: string;
    brandName: string;
    page: number;
  }
  query: {
    loading: boolean;
    error: boolean;
    results: DrugsFdaResponse | null;
  }
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
        error: false,
        results: null
      }
    };
  },
  getters: {
    numberOfPages: (state) => {
      if (state.query.loading || state.query.error || !state.query.results) {
        return null;
      }
      return Math.ceil(state.query.results.meta.results.total / state.query.results.meta.results.limit);
    },
    searchDisabled: (state) => (state.parameters.manufacturerName.length < 2 || state.query.loading)
  },
  actions: {
    search(pageToSearch = 1) {
      const { apiEndpoint } = useAppStore();
      this.parameters.page = pageToSearch;
      this.query = {
        loading: true,
        error: false,
        results: null
      };
      ((this.parameters.brandName.length === 0) ?
          getDrugsManufacturer(apiEndpoint, this.parameters.manufacturerName, pageToSearch) :
          getDrugsManufacturerBrand(apiEndpoint, this.parameters.manufacturerName, this.parameters.brandName, pageToSearch))
          .then(response => {
            this.query = {
              loading: false,
              error: false,
              results: response
            };
          }).catch(ignored => {
            this.query = {
              loading: false,
              error: true,
              results: null
            };
        })
    }
  },
});
