import { mande } from 'mande';
import type {ServerVersion} from "@/types/drafModels";

/**
 *
 */
export function getVersion(url: string): Promise<ServerVersion> {
  const version = mande(url);
  return version.get<ServerVersion>('/api/v1/version');
}
