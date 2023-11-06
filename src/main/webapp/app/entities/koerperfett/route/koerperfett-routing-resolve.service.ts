import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKoerperfett } from '../koerperfett.model';
import { KoerperfettService } from '../service/koerperfett.service';

@Injectable({ providedIn: 'root' })
export class KoerperfettRoutingResolveService implements Resolve<IKoerperfett | null> {
  constructor(protected service: KoerperfettService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKoerperfett | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((koerperfett: HttpResponse<IKoerperfett>) => {
          if (koerperfett.body) {
            return of(koerperfett.body);
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
