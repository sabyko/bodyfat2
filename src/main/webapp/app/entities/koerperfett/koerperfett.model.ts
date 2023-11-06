import dayjs from 'dayjs/esm';

export interface IKoerperfett {
  id: number;
  privatoderfirma?: boolean | null;
  koerpergroesse?: number | null;
  nackenumfang?: number | null;
  bauchumfang?: number | null;
  hueftumfang?: number | null;
  geschlecht?: string | null;
  age?: number | null;
  koerperfettanteil?: number | null;
  datumundZeit?: dayjs.Dayjs | null;
  url?: string | null;
  success?: boolean | null;
  errorMessage?: string | null;
}

export type NewKoerperfett = Omit<IKoerperfett, 'id'> & { id: null };
