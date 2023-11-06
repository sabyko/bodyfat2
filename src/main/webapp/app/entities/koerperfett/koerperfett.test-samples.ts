import dayjs from 'dayjs/esm';

import { IKoerperfett, NewKoerperfett } from './koerperfett.model';

export const sampleWithRequiredData: IKoerperfett = {
  id: 57705,
  privatoderfirma: true,
  koerpergroesse: 77607,
  nackenumfang: 55807,
  bauchumfang: 42409,
  geschlecht: 'Ringgit driver port',
  age: 12681,
};

export const sampleWithPartialData: IKoerperfett = {
  id: 10720,
  privatoderfirma: true,
  koerpergroesse: 48746,
  nackenumfang: 96746,
  bauchumfang: 29948,
  hueftumfang: 886,
  geschlecht: 'engage Franc Cape',
  age: 10495,
  success: true,
  errorMessage: 'Bedfordshire JBOD',
};

export const sampleWithFullData: IKoerperfett = {
  id: 9755,
  privatoderfirma: false,
  koerpergroesse: 23572,
  nackenumfang: 40044,
  bauchumfang: 20567,
  hueftumfang: 50493,
  geschlecht: 'Practical Kina deploy',
  age: 37900,
  koerperfettanteil: 57911,
  datumundZeit: dayjs('2023-11-06T13:13'),
  url: 'http://vivienne.info',
  success: false,
  errorMessage: 'withdrawal Books Savings',
};

export const sampleWithNewData: NewKoerperfett = {
  privatoderfirma: true,
  koerpergroesse: 93522,
  nackenumfang: 70723,
  bauchumfang: 4053,
  geschlecht: 'Dollar efficient Loan',
  age: 98036,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
