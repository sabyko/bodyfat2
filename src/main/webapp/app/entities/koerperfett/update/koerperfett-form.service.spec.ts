import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../koerperfett.test-samples';

import { KoerperfettFormService } from './koerperfett-form.service';

describe('Koerperfett Form Service', () => {
  let service: KoerperfettFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KoerperfettFormService);
  });

  describe('Service methods', () => {
    describe('createKoerperfettFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKoerperfettFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            privatoderfirma: expect.any(Object),
            koerpergroesse: expect.any(Object),
            nackenumfang: expect.any(Object),
            bauchumfang: expect.any(Object),
            hueftumfang: expect.any(Object),
            geschlecht: expect.any(Object),
            age: expect.any(Object),
            koerperfettanteil: expect.any(Object),
            datumundZeit: expect.any(Object),
            url: expect.any(Object),
            success: expect.any(Object),
            errorMessage: expect.any(Object),
          })
        );
      });

      it('passing IKoerperfett should create a new form with FormGroup', () => {
        const formGroup = service.createKoerperfettFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            privatoderfirma: expect.any(Object),
            koerpergroesse: expect.any(Object),
            nackenumfang: expect.any(Object),
            bauchumfang: expect.any(Object),
            hueftumfang: expect.any(Object),
            geschlecht: expect.any(Object),
            age: expect.any(Object),
            koerperfettanteil: expect.any(Object),
            datumundZeit: expect.any(Object),
            url: expect.any(Object),
            success: expect.any(Object),
            errorMessage: expect.any(Object),
          })
        );
      });
    });

    describe('getKoerperfett', () => {
      it('should return NewKoerperfett for default Koerperfett initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createKoerperfettFormGroup(sampleWithNewData);

        const koerperfett = service.getKoerperfett(formGroup) as any;

        expect(koerperfett).toMatchObject(sampleWithNewData);
      });

      it('should return NewKoerperfett for empty Koerperfett initial value', () => {
        const formGroup = service.createKoerperfettFormGroup();

        const koerperfett = service.getKoerperfett(formGroup) as any;

        expect(koerperfett).toMatchObject({});
      });

      it('should return IKoerperfett', () => {
        const formGroup = service.createKoerperfettFormGroup(sampleWithRequiredData);

        const koerperfett = service.getKoerperfett(formGroup) as any;

        expect(koerperfett).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKoerperfett should not enable id FormControl', () => {
        const formGroup = service.createKoerperfettFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKoerperfett should disable id FormControl', () => {
        const formGroup = service.createKoerperfettFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
