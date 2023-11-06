import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { KoerperfettFormService, KoerperfettFormGroup } from './koerperfett-form.service';
import { IKoerperfett } from '../koerperfett.model';
import { KoerperfettService } from '../service/koerperfett.service';

@Component({
  selector: 'jhi-koerperfett-update',
  templateUrl: './koerperfett-update.component.html',
})
export class KoerperfettUpdateComponent implements OnInit {
  isSaving = false;
  koerperfett: IKoerperfett | null = null;

  editForm: KoerperfettFormGroup = this.koerperfettFormService.createKoerperfettFormGroup();

  constructor(
    protected koerperfettService: KoerperfettService,
    protected koerperfettFormService: KoerperfettFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ koerperfett }) => {
      this.koerperfett = koerperfett;
      if (koerperfett) {
        this.updateForm(koerperfett);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const koerperfett = this.koerperfettFormService.getKoerperfett(this.editForm);
    if (koerperfett.id !== null) {
      this.subscribeToSaveResponse(this.koerperfettService.update(koerperfett));
    } else {
      this.subscribeToSaveResponse(this.koerperfettService.create(koerperfett));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKoerperfett>>): void {
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

  protected updateForm(koerperfett: IKoerperfett): void {
    this.koerperfett = koerperfett;
    this.koerperfettFormService.resetForm(this.editForm, koerperfett);
  }
}
