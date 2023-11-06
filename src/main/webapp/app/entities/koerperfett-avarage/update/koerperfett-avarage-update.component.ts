import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { KoerperfettAvarageFormService, KoerperfettAvarageFormGroup } from './koerperfett-avarage-form.service';
import { IKoerperfettAvarage } from '../koerperfett-avarage.model';
import { KoerperfettAvarageService } from '../service/koerperfett-avarage.service';

@Component({
  selector: 'jhi-koerperfett-avarage-update',
  templateUrl: './koerperfett-avarage-update.component.html',
})
export class KoerperfettAvarageUpdateComponent implements OnInit {
  isSaving = false;
  koerperfettAvarage: IKoerperfettAvarage | null = null;

  editForm: KoerperfettAvarageFormGroup = this.koerperfettAvarageFormService.createKoerperfettAvarageFormGroup();

  constructor(
    protected koerperfettAvarageService: KoerperfettAvarageService,
    protected koerperfettAvarageFormService: KoerperfettAvarageFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ koerperfettAvarage }) => {
      this.koerperfettAvarage = koerperfettAvarage;
      if (koerperfettAvarage) {
        this.updateForm(koerperfettAvarage);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const koerperfettAvarage = this.koerperfettAvarageFormService.getKoerperfettAvarage(this.editForm);
    if (koerperfettAvarage.id !== null) {
      this.subscribeToSaveResponse(this.koerperfettAvarageService.update(koerperfettAvarage));
    } else {
      this.subscribeToSaveResponse(this.koerperfettAvarageService.create(koerperfettAvarage));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKoerperfettAvarage>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(koerperfettAvarage: IKoerperfettAvarage): void {
    this.koerperfettAvarage = koerperfettAvarage;
    this.koerperfettAvarageFormService.resetForm(this.editForm, koerperfettAvarage);
  }
}
