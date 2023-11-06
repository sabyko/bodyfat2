import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IKoerperfett, NewKoerperfett } from '../koerperfett.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKoerperfett for edit and NewKoerperfettFormGroupInput for create.
 */
type KoerperfettFormGroupInput = IKoerperfett | PartialWithRequiredKeyOf<NewKoerperfett>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IKoerperfett | NewKoerperfett> = Omit<T, 'datumundZeit'> & {
  datumundZeit?: string | null;
};

type KoerperfettFormRawValue = FormValueOf<IKoerperfett>;

type NewKoerperfettFormRawValue = FormValueOf<NewKoerperfett>;

type KoerperfettFormDefaults = Pick<NewKoerperfett, 'id' | 'privatoderfirma' | 'datumundZeit' | 'success'>;

type KoerperfettFormGroupContent = {
  id: FormControl<KoerperfettFormRawValue['id'] | NewKoerperfett['id']>;
  privatoderfirma: FormControl<KoerperfettFormRawValue['privatoderfirma']>;
  koerpergroesse: FormControl<KoerperfettFormRawValue['koerpergroesse']>;
  nackenumfang: FormControl<KoerperfettFormRawValue['nackenumfang']>;
  bauchumfang: FormControl<KoerperfettFormRawValue['bauchumfang']>;
  hueftumfang: FormControl<KoerperfettFormRawValue['hueftumfang']>;
  geschlecht: FormControl<KoerperfettFormRawValue['geschlecht']>;
  age: FormControl<KoerperfettFormRawValue['age']>;
  koerperfettanteil: FormControl<KoerperfettFormRawValue['koerperfettanteil']>;
  datumundZeit: FormControl<KoerperfettFormRawValue['datumundZeit']>;
  url: FormControl<KoerperfettFormRawValue['url']>;
  success: FormControl<KoerperfettFormRawValue['success']>;
  errorMessage: FormControl<KoerperfettFormRawValue['errorMessage']>;
};

export type KoerperfettFormGroup = FormGroup<KoerperfettFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KoerperfettFormService {
  createKoerperfettFormGroup(koerperfett: KoerperfettFormGroupInput = { id: null }): KoerperfettFormGroup {
    const koerperfettRawValue = this.convertKoerperfettToKoerperfettRawValue({
      ...this.getFormDefaults(),
      ...koerperfett,
    });
    return new FormGroup<KoerperfettFormGroupContent>({
      id: new FormControl(
        { value: koerperfettRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      privatoderfirma: new FormControl(koerperfettRawValue.privatoderfirma, {
        validators: [Validators.required],
      }),
      koerpergroesse: new FormControl(koerperfettRawValue.koerpergroesse, {
        validators: [Validators.required],
      }),
      nackenumfang: new FormControl(koerperfettRawValue.nackenumfang, {
        validators: [Validators.required],
      }),
      bauchumfang: new FormControl(koerperfettRawValue.bauchumfang, {
        validators: [Validators.required],
      }),
      hueftumfang: new FormControl(koerperfettRawValue.hueftumfang),
      geschlecht: new FormControl(koerperfettRawValue.geschlecht, {
        validators: [Validators.required],
      }),
      age: new FormControl(koerperfettRawValue.age, {
        validators: [Validators.required],
      }),
      koerperfettanteil: new FormControl(koerperfettRawValue.koerperfettanteil),
      datumundZeit: new FormControl(koerperfettRawValue.datumundZeit),
      url: new FormControl(koerperfettRawValue.url),
      success: new FormControl(koerperfettRawValue.success),
      errorMessage: new FormControl(koerperfettRawValue.errorMessage),
    });
  }

  getKoerperfett(form: KoerperfettFormGroup): IKoerperfett | NewKoerperfett {
    return this.convertKoerperfettRawValueToKoerperfett(form.getRawValue() as KoerperfettFormRawValue | NewKoerperfettFormRawValue);
  }

  resetForm(form: KoerperfettFormGroup, koerperfett: KoerperfettFormGroupInput): void {
    const koerperfettRawValue = this.convertKoerperfettToKoerperfettRawValue({ ...this.getFormDefaults(), ...koerperfett });
    form.reset(
      {
        ...koerperfettRawValue,
        id: { value: koerperfettRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): KoerperfettFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      privatoderfirma: false,
      datumundZeit: currentTime,
      success: false,
    };
  }

  private convertKoerperfettRawValueToKoerperfett(
    rawKoerperfett: KoerperfettFormRawValue | NewKoerperfettFormRawValue
  ): IKoerperfett | NewKoerperfett {
    return {
      ...rawKoerperfett,
      datumundZeit: dayjs(rawKoerperfett.datumundZeit, DATE_TIME_FORMAT),
    };
  }

  private convertKoerperfettToKoerperfettRawValue(
    koerperfett: IKoerperfett | (Partial<NewKoerperfett> & KoerperfettFormDefaults)
  ): KoerperfettFormRawValue | PartialWithRequiredKeyOf<NewKoerperfettFormRawValue> {
    return {
      ...koerperfett,
      datumundZeit: koerperfett.datumundZeit ? koerperfett.datumundZeit.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
