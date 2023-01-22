import { mande } from 'mande';
import type {BackVersion} from "@/types/drafModels";

/**
 *
 */
export function getVersion(url: string): Promise<BackVersion> {
  const version = mande(url);
  return version.get<BackVersion>('/api/v1/version');
}
