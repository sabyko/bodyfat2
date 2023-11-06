import dayjs from 'dayjs/esm';

export interface IKoerperfettAvarage {
  id: number;
  geschlecht?: string | null;
  alter?: number | null;
  koerperfettanteil?: number | null;
  datumundZeit?: dayjs.Dayjs | null;
}

export type NewKoerperfettAvarage = Omit<IKoerperfettAvarage, 'id'> & { id: null };
