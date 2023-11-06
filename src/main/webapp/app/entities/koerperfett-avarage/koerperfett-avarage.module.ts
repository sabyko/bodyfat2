import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KoerperfettAvarageComponent } from './list/koerperfett-avarage.component';
import { KoerperfettAvarageDetailComponent } from './detail/koerperfett-avarage-detail.component';
import { KoerperfettAvarageUpdateComponent } from './update/koerperfett-avarage-update.component';
import { KoerperfettAvarageDeleteDialogComponent } from './delete/koerperfett-avarage-delete-dialog.component';
import { KoerperfettAvarageRoutingModule } from './route/koerperfett-avarage-routing.module';

@NgModule({
  imports: [SharedModule, KoerperfettAvarageRoutingModule],
  declarations: [
    KoerperfettAvarageComponent,
    KoerperfettAvarageDetailComponent,
    KoerperfettAvarageUpdateComponent,
    KoerperfettAvarageDeleteDialogComponent,
  ],
})
export class KoerperfettAvarageModule {}
