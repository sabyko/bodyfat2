import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { KoerperfettComponent } from './list/koerperfett.component';
import { KoerperfettDetailComponent } from './detail/koerperfett-detail.component';
import { KoerperfettUpdateComponent } from './update/koerperfett-update.component';
import { KoerperfettDeleteDialogComponent } from './delete/koerperfett-delete-dialog.component';
import { KoerperfettRoutingModule } from './route/koerperfett-routing.module';

@NgModule({
  imports: [SharedModule, KoerperfettRoutingModule],
  declarations: [KoerperfettComponent, KoerperfettDetailComponent, KoerperfettUpdateComponent, KoerperfettDeleteDialogComponent],
})
export class KoerperfettModule {}
