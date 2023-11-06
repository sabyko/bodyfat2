import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { KoerperfettFormService } from './koerperfett-form.service';
import { KoerperfettService } from '../service/koerperfett.service';
import { IKoerperfett } from '../koerperfett.model';

import { KoerperfettUpdateComponent } from './koerperfett-update.component';

describe('Koerperfett Management Update Component', () => {
  let comp: KoerperfettUpdateComponent;
  let fixture: ComponentFixture<KoerperfettUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let koerperfettFormService: KoerperfettFormService;
  let koerperfettService: KoerperfettService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [KoerperfettUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(KoerperfettUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KoerperfettUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    koerperfettFormService = TestBed.inject(KoerperfettFormService);
    koerperfettService = TestBed.inject(KoerperfettService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const koerperfett: IKoerperfett = { id: 456 };

      activatedRoute.data = of({ koerperfett });
      comp.ngOnInit();

      expect(comp.koerperfett).toEqual(koerperfett);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKoerperfett>>();
      const koerperfett = { id: 123 };
      jest.spyOn(koerperfettFormService, 'getKoerperfett').mockReturnValue(koerperfett);
      jest.spyOn(koerperfettService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ koerperfett });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: koerperfett }));
      saveSubject.complete();

      // THEN
      expect(koerperfettFormService.getKoerperfett).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(koerperfettService.update).toHaveBeenCalledWith(expect.objectContaining(koerperfett));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKoerperfett>>();
      const koerperfett = { id: 123 };
      jest.spyOn(koerperfettFormService, 'getKoerperfett').mockReturnValue({ id: null });
      jest.spyOn(koerperfettService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ koerperfett: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: koerperfett }));
      saveSubject.complete();

      // THEN
      expect(koerperfettFormService.getKoerperfett).toHaveBeenCalled();
      expect(koerperfettService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKoerperfett>>();
      const koerperfett = { id: 123 };
      jest.spyOn(koerperfettService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ koerperfett });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(koerperfettService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
