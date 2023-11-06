import dayjs from 'dayjs/esm';

import { IKoerperfettAvarage, NewKoerperfettAvarage } from './koerperfett-avarage.model';

export const sampleWithRequiredData: IKoerperfettAvarage = {
  id: 25436,
};

export const sampleWithPartialData: IKoerperfettAvarage = {
  id: 99673,
  koerperfettanteil: 95532,
};

export const sampleWithFullData: IKoerperfettAvarage = {
  id: 39055,
  geschlecht: 'parsing',
  alter: 45221,
  koerperfettanteil: 97200,
  datumundZeit: dayjs('2023-11-05T17:26'),
};

export const sampleWithNewData: NewKoerperfettAvarage = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
