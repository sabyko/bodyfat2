import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../koerperfett-avarage.test-samples';

import { KoerperfettAvarageFormService } from './koerperfett-avarage-form.service';

describe('KoerperfettAvarage Form Service', () => {
  let service: KoerperfettAvarageFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KoerperfettAvarageFormService);
  });

  describe('Service methods', () => {
    describe('createKoerperfettAvarageFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKoerperfettAvarageFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            geschlecht: expect.any(Object),
            alter: expect.any(Object),
            koerperfettanteil: expect.any(Object),
            datumundZeit: expect.any(Object),
          })
        );
      });

      it('passing IKoerperfettAvarage should create a new form with FormGroup', () => {
        const formGroup = service.createKoerperfettAvarageFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            geschlecht: expect.any(Object),
            alter: expect.any(Object),
            koerperfettanteil: expect.any(Object),
            datumundZeit: expect.any(Object),
          })
        );
      });
    });

    describe('getKoerperfettAvarage', () => {
      it('should return NewKoerperfettAvarage for default KoerperfettAvarage initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createKoerperfettAvarageFormGroup(sampleWithNewData);

        const koerperfettAvarage = service.getKoerperfettAvarage(formGroup) as any;

        expect(koerperfettAvarage).toMatchObject(sampleWithNewData);
      });

      it('should return NewKoerperfettAvarage for empty KoerperfettAvarage initial value', () => {
        const formGroup = service.createKoerperfettAvarageFormGroup();

        const koerperfettAvarage = service.getKoerperfettAvarage(formGroup) as any;

        expect(koerperfettAvarage).toMatchObject({});
      });

      it('should return IKoerperfettAvarage', () => {
        const formGroup = service.createKoerperfettAvarageFormGroup(sampleWithRequiredData);

        const koerperfettAvarage = service.getKoerperfettAvarage(formGroup) as any;

        expect(koerperfettAvarage).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKoerperfettAvarage should not enable id FormControl', () => {
        const formGroup = service.createKoerperfettAvarageFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKoerperfettAvarage should disable id FormControl', () => {
        const formGroup = service.createKoerperfettAvarageFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
