import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IKoerperfettAvarage } from '../koerperfett-avarage.model';
import { KoerperfettAvarageService } from '../service/koerperfett-avarage.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './koerperfett-avarage-delete-dialog.component.html',
})
export class KoerperfettAvarageDeleteDialogComponent {
  koerperfettAvarage?: IKoerperfettAvarage;

  constructor(protected koerperfettAvarageService: KoerperfettAvarageService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.koerperfettAvarageService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
