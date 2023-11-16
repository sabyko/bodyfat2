import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BodyfatComponent } from 'app/bodyfat/bodyfat.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'koerperfett',
        data: { pageTitle: 'bodyfatApp.koerperfett.home.title' },
        loadChildren: () => import('./koerperfett/koerperfett.module').then(m => m.KoerperfettModule),
      },
      {
        path: 'koerperfett-avarage',
        data: { pageTitle: 'bodyfatApp.koerperfettAvarage.home.title' },
        loadChildren: () => import('./koerperfett-avarage/koerperfett-avarage.module').then(m => m.KoerperfettAvarageModule),
      },
      {
        path: 'bodyfat',
        component: BodyfatComponent,
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
