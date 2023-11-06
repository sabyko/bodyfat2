import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { KoerperfettAvarageFormService } from './koerperfett-avarage-form.service';
import { KoerperfettAvarageService } from '../service/koerperfett-avarage.service';
import { IKoerperfettAvarage } from '../koerperfett-avarage.model';

import { KoerperfettAvarageUpdateComponent } from './koerperfett-avarage-update.component';

describe('KoerperfettAvarage Management Update Component', () => {
  let comp: KoerperfettAvarageUpdateComponent;
  let fixture: ComponentFixture<KoerperfettAvarageUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let koerperfettAvarageFormService: KoerperfettAvarageFormService;
  let koerperfettAvarageService: KoerperfettAvarageService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [KoerperfettAvarageUpdateComponent],
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
      .overrideTemplate(KoerperfettAvarageUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KoerperfettAvarageUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    koerperfettAvarageFormService = TestBed.inject(KoerperfettAvarageFormService);
    koerperfettAvarageService = TestBed.inject(KoerperfettAvarageService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const koerperfettAvarage: IKoerperfettAvarage = { id: 456 };

      activatedRoute.data = of({ koerperfettAvarage });
      comp.ngOnInit();

      expect(comp.koerperfettAvarage).toEqual(koerperfettAvarage);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKoerperfettAvarage>>();
      const koerperfettAvarage = { id: 123 };
      jest.spyOn(koerperfettAvarageFormService, 'getKoerperfettAvarage').mockReturnValue(koerperfettAvarage);
      jest.spyOn(koerperfettAvarageService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ koerperfettAvarage });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: koerperfettAvarage }));
      saveSubject.complete();

      // THEN
      expect(koerperfettAvarageFormService.getKoerperfettAvarage).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(koerperfettAvarageService.update).toHaveBeenCalledWith(expect.objectContaining(koerperfettAvarage));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKoerperfettAvarage>>();
      const koerperfettAvarage = { id: 123 };
      jest.spyOn(koerperfettAvarageFormService, 'getKoerperfettAvarage').mockReturnValue({ id: null });
      jest.spyOn(koerperfettAvarageService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ koerperfettAvarage: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: koerperfettAvarage }));
      saveSubject.complete();

      // THEN
      expect(koerperfettAvarageFormService.getKoerperfettAvarage).toHaveBeenCalled();
      expect(koerperfettAvarageService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKoerperfettAvarage>>();
      const koerperfettAvarage = { id: 123 };
      jest.spyOn(koerperfettAvarageService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ koerperfettAvarage });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(koerperfettAvarageService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
