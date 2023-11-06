import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KoerperfettAvarageDetailComponent } from './koerperfett-avarage-detail.component';

describe('KoerperfettAvarage Management Detail Component', () => {
  let comp: KoerperfettAvarageDetailComponent;
  let fixture: ComponentFixture<KoerperfettAvarageDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KoerperfettAvarageDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ koerperfettAvarage: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(KoerperfettAvarageDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KoerperfettAvarageDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load koerperfettAvarage on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.koerperfettAvarage).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
