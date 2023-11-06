import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKoerperfett, NewKoerperfett } from '../koerperfett.model';

export type PartialUpdateKoerperfett = Partial<IKoerperfett> & Pick<IKoerperfett, 'id'>;

type RestOf<T extends IKoerperfett | NewKoerperfett> = Omit<T, 'datumundZeit'> & {
  datumundZeit?: string | null;
};

export type RestKoerperfett = RestOf<IKoerperfett>;

export type NewRestKoerperfett = RestOf<NewKoerperfett>;

export type PartialUpdateRestKoerperfett = RestOf<PartialUpdateKoerperfett>;

export type EntityResponseType = HttpResponse<IKoerperfett>;
export type EntityArrayResponseType = HttpResponse<IKoerperfett[]>;

@Injectable({ providedIn: 'root' })
export class KoerperfettService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/koerperfetts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(koerperfett: NewKoerperfett): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(koerperfett);
    return this.http
      .post<RestKoerperfett>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(koerperfett: IKoerperfett): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(koerperfett);
    return this.http
      .put<RestKoerperfett>(`${this.resourceUrl}/${this.getKoerperfettIdentifier(koerperfett)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(koerperfett: PartialUpdateKoerperfett): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(koerperfett);
    return this.http
      .patch<RestKoerperfett>(`${this.resourceUrl}/${this.getKoerperfettIdentifier(koerperfett)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestKoerperfett>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestKoerperfett[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKoerperfettIdentifier(koerperfett: Pick<IKoerperfett, 'id'>): number {
    return koerperfett.id;
  }

  compareKoerperfett(o1: Pick<IKoerperfett, 'id'> | null, o2: Pick<IKoerperfett, 'id'> | null): boolean {
    return o1 && o2 ? this.getKoerperfettIdentifier(o1) === this.getKoerperfettIdentifier(o2) : o1 === o2;
  }

  addKoerperfettToCollectionIfMissing<Type extends Pick<IKoerperfett, 'id'>>(
    koerperfettCollection: Type[],
    ...koerperfettsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const koerperfetts: Type[] = koerperfettsToCheck.filter(isPresent);
    if (koerperfetts.length > 0) {
      const koerperfettCollectionIdentifiers = koerperfettCollection.map(
        koerperfettItem => this.getKoerperfettIdentifier(koerperfettItem)!
      );
      const koerperfettsToAdd = koerperfetts.filter(koerperfettItem => {
        const koerperfettIdentifier = this.getKoerperfettIdentifier(koerperfettItem);
        if (koerperfettCollectionIdentifiers.includes(koerperfettIdentifier)) {
          return false;
        }
        koerperfettCollectionIdentifiers.push(koerperfettIdentifier);
        return true;
      });
      return [...koerperfettsToAdd, ...koerperfettCollection];
    }
    return koerperfettCollection;
  }

  protected convertDateFromClient<T extends IKoerperfett | NewKoerperfett | PartialUpdateKoerperfett>(koerperfett: T): RestOf<T> {
    return {
      ...koerperfett,
      datumundZeit: koerperfett.datumundZeit?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restKoerperfett: RestKoerperfett): IKoerperfett {
    return {
      ...restKoerperfett,
      datumundZeit: restKoerperfett.datumundZeit ? dayjs(restKoerperfett.datumundZeit) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestKoerperfett>): HttpResponse<IKoerperfett> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestKoerperfett[]>): HttpResponse<IKoerperfett[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
