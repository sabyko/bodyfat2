import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KoerperfettComponent } from '../list/koerperfett.component';
import { KoerperfettDetailComponent } from '../detail/koerperfett-detail.component';
import { KoerperfettUpdateComponent } from '../update/koerperfett-update.component';
import { KoerperfettRoutingResolveService } from './koerperfett-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const koerperfettRoute: Routes = [
  {
    path: '',
    component: KoerperfettComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KoerperfettDetailComponent,
    resolve: {
      koerperfett: KoerperfettRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KoerperfettUpdateComponent,
    resolve: {
      koerperfett: KoerperfettRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KoerperfettUpdateComponent,
    resolve: {
      koerperfett: KoerperfettRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(koerperfettRoute)],
  exports: [RouterModule],
})
export class KoerperfettRoutingModule {}
