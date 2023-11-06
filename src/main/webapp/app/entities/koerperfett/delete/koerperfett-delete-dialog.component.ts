import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IKoerperfett } from '../koerperfett.model';
import { KoerperfettService } from '../service/koerperfett.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './koerperfett-delete-dialog.component.html',
})
export class KoerperfettDeleteDialogComponent {
  koerperfett?: IKoerperfett;

  constructor(protected koerperfettService: KoerperfettService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.koerperfettService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
