declare module 'vuetify/lib/util/colors.mjs';

interface ImportMetaEnv {
  // see https://vitejs.dev/guide/env-and-mode.html#env-files
  // add .env variables.
  readonly VITE_APP_TITLE: string;
  readonly VITE_APP_COPYRIGHT: string;
  readonly VITE_APP_DEFAULT_THEME: 'dark' | 'light';
  readonly VITE_APP_BASE_URL: string;
}

// eslint-disable-next-line no-unused-vars
interface ImportMeta {
  readonly env: ImportMetaEnv;
}
