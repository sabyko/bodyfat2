import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKoerperfettAvarage } from '../koerperfett-avarage.model';

@Component({
  selector: 'jhi-koerperfett-avarage-detail',
  templateUrl: './koerperfett-avarage-detail.component.html',
})
export class KoerperfettAvarageDetailComponent implements OnInit {
  koerperfettAvarage: IKoerperfettAvarage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ koerperfettAvarage }) => {
      this.koerperfettAvarage = koerperfettAvarage;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
