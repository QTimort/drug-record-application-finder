/**
 *
 */
export function getEnvOrDefault(): ImportMetaEnv {
  const defaultEnv: ImportMetaEnv = {
    VITE_APP_TITLE: 'Application Name',
    VITE_APP_COPYRIGHT: '© Application Copyright',
    VITE_APP_DEFAULT_THEME: 'light',
    VITE_APP_BASE_URL: '/',
    VITE_APP_SERVER_URL: 'http://localhost:8080',
  };
  const env: ImportMetaEnv = import.meta.env;

  return {
    ...defaultEnv,
    ...env,
  };
}
