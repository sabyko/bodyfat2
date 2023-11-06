import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IKoerperfettAvarage, NewKoerperfettAvarage } from '../koerperfett-avarage.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKoerperfettAvarage for edit and NewKoerperfettAvarageFormGroupInput for create.
 */
type KoerperfettAvarageFormGroupInput = IKoerperfettAvarage | PartialWithRequiredKeyOf<NewKoerperfettAvarage>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IKoerperfettAvarage | NewKoerperfettAvarage> = Omit<T, 'datumundZeit'> & {
  datumundZeit?: string | null;
};

type KoerperfettAvarageFormRawValue = FormValueOf<IKoerperfettAvarage>;

type NewKoerperfettAvarageFormRawValue = FormValueOf<NewKoerperfettAvarage>;

type KoerperfettAvarageFormDefaults = Pick<NewKoerperfettAvarage, 'id' | 'datumundZeit'>;

type KoerperfettAvarageFormGroupContent = {
  id: FormControl<KoerperfettAvarageFormRawValue['id'] | NewKoerperfettAvarage['id']>;
  geschlecht: FormControl<KoerperfettAvarageFormRawValue['geschlecht']>;
  alter: FormControl<KoerperfettAvarageFormRawValue['alter']>;
  koerperfettanteil: FormControl<KoerperfettAvarageFormRawValue['koerperfettanteil']>;
  datumundZeit: FormControl<KoerperfettAvarageFormRawValue['datumundZeit']>;
};

export type KoerperfettAvarageFormGroup = FormGroup<KoerperfettAvarageFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KoerperfettAvarageFormService {
  createKoerperfettAvarageFormGroup(koerperfettAvarage: KoerperfettAvarageFormGroupInput = { id: null }): KoerperfettAvarageFormGroup {
    const koerperfettAvarageRawValue = this.convertKoerperfettAvarageToKoerperfettAvarageRawValue({
      ...this.getFormDefaults(),
      ...koerperfettAvarage,
    });
    return new FormGroup<KoerperfettAvarageFormGroupContent>({
      id: new FormControl(
        { value: koerperfettAvarageRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      geschlecht: new FormControl(koerperfettAvarageRawValue.geschlecht),
      alter: new FormControl(koerperfettAvarageRawValue.alter),
      koerperfettanteil: new FormControl(koerperfettAvarageRawValue.koerperfettanteil),
      datumundZeit: new FormControl(koerperfettAvarageRawValue.datumundZeit),
    });
  }

  getKoerperfettAvarage(form: KoerperfettAvarageFormGroup): IKoerperfettAvarage | NewKoerperfettAvarage {
    return this.convertKoerperfettAvarageRawValueToKoerperfettAvarage(
      form.getRawValue() as KoerperfettAvarageFormRawValue | NewKoerperfettAvarageFormRawValue
    );
  }

  resetForm(form: KoerperfettAvarageFormGroup, koerperfettAvarage: KoerperfettAvarageFormGroupInput): void {
    const koerperfettAvarageRawValue = this.convertKoerperfettAvarageToKoerperfettAvarageRawValue({
      ...this.getFormDefaults(),
      ...koerperfettAvarage,
    });
    form.reset(
      {
        ...koerperfettAvarageRawValue,
        id: { value: koerperfettAvarageRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): KoerperfettAvarageFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      datumundZeit: currentTime,
    };
  }

  private convertKoerperfettAvarageRawValueToKoerperfettAvarage(
    rawKoerperfettAvarage: KoerperfettAvarageFormRawValue | NewKoerperfettAvarageFormRawValue
  ): IKoerperfettAvarage | NewKoerperfettAvarage {
    return {
      ...rawKoerperfettAvarage,
      datumundZeit: dayjs(rawKoerperfettAvarage.datumundZeit, DATE_TIME_FORMAT),
    };
  }

  private convertKoerperfettAvarageToKoerperfettAvarageRawValue(
    koerperfettAvarage: IKoerperfettAvarage | (Partial<NewKoerperfettAvarage> & KoerperfettAvarageFormDefaults)
  ): KoerperfettAvarageFormRawValue | PartialWithRequiredKeyOf<NewKoerperfettAvarageFormRawValue> {
    return {
      ...koerperfettAvarage,
      datumundZeit: koerperfettAvarage.datumundZeit ? koerperfettAvarage.datumundZeit.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
