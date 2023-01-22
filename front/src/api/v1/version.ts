import { mande } from 'mande';

/**
 *
 */
export function getVersion(url: string) {
  const version = mande(url);
  // todo set type
  return version.get('/api/v1/version');
}
