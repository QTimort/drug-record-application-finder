import { mande } from 'mande';
import type {DrugsFdaResponse} from "@/types/drafModels";

/**
 *
 */
export function getDrugsManufacturer(url: string, manufacturer: string): Promise<DrugsFdaResponse> {
  const version = mande(url);
  return version.get<DrugsFdaResponse>(`/api/v1/drugs/manufacturers/${manufacturer}`);
}

export function getDrugsManufacturerBrand(url: string, manufacturer: string, brand: string): Promise<DrugsFdaResponse> {
  const version = mande(url);
  return version.get<DrugsFdaResponse>(`/api/v1/drugs/manufacturers/${manufacturer}/brands/${brand}`);
}
