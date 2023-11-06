import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

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
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
