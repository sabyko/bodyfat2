import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKoerperfettAvarage } from '../koerperfett-avarage.model';
import { KoerperfettAvarageService } from '../service/koerperfett-avarage.service';

@Injectable({ providedIn: 'root' })
export class KoerperfettAvarageRoutingResolveService implements Resolve<IKoerperfettAvarage | null> {
  constructor(protected service: KoerperfettAvarageService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKoerperfettAvarage | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((koerperfettAvarage: HttpResponse<IKoerperfettAvarage>) => {
          if (koerperfettAvarage.body) {
            return of(koerperfettAvarage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
