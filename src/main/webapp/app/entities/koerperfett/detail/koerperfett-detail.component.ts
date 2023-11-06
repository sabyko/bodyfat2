import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKoerperfett } from '../koerperfett.model';

@Component({
  selector: 'jhi-koerperfett-detail',
  templateUrl: './koerperfett-detail.component.html',
})
export class KoerperfettDetailComponent implements OnInit {
  koerperfett: IKoerperfett | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ koerperfett }) => {
      this.koerperfett = koerperfett;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
