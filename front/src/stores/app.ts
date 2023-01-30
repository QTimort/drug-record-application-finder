// Utilities
import { defineStore } from 'pinia';
import { getVersion } from '@/api/v1/version';
import { getEnvOrDefault } from '@/config/env';
import { defineInitializableStore } from '@/stores/defineInitializableStore';
import type { ServerVersion } from '@/types/drafModels';

export interface AppState {
  apiEndpoint: string;
  serverVersion: ServerVersion | null;
}

export type AsyncError = { error?: unknown };

export const useAppStore = defineInitializableStore(
  defineStore('app', {
    state: (): AppState => {
      return {
        apiEndpoint: getEnvOrDefault().VITE_APP_SERVER_URL,
        serverVersion: null,
      };
    },
    actions: {
      async setApiEndpoint(apiEndpoint: string): Promise<AsyncError> {
        // todo validate url
        this.apiEndpoint = apiEndpoint;
        try {
          this.serverVersion = await getVersion(this.apiEndpoint);
          return {};
        } catch (e: unknown) {
          return {
            error: e,
          };
        }
      },
    },
  }),
  store => {
    store.setApiEndpoint(store.apiEndpoint);
  }
);
