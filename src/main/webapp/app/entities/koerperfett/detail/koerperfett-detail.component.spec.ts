import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KoerperfettDetailComponent } from './koerperfett-detail.component';

describe('Koerperfett Management Detail Component', () => {
  let comp: KoerperfettDetailComponent;
  let fixture: ComponentFixture<KoerperfettDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KoerperfettDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ koerperfett: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(KoerperfettDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(KoerperfettDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load koerperfett on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.koerperfett).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
