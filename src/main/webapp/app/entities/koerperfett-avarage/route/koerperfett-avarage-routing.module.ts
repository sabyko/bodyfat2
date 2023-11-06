import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { KoerperfettAvarageComponent } from '../list/koerperfett-avarage.component';
import { KoerperfettAvarageDetailComponent } from '../detail/koerperfett-avarage-detail.component';
import { KoerperfettAvarageUpdateComponent } from '../update/koerperfett-avarage-update.component';
import { KoerperfettAvarageRoutingResolveService } from './koerperfett-avarage-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const koerperfettAvarageRoute: Routes = [
  {
    path: '',
    component: KoerperfettAvarageComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KoerperfettAvarageDetailComponent,
    resolve: {
      koerperfettAvarage: KoerperfettAvarageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KoerperfettAvarageUpdateComponent,
    resolve: {
      koerperfettAvarage: KoerperfettAvarageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KoerperfettAvarageUpdateComponent,
    resolve: {
      koerperfettAvarage: KoerperfettAvarageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(koerperfettAvarageRoute)],
  exports: [RouterModule],
})
export class KoerperfettAvarageRoutingModule {}
