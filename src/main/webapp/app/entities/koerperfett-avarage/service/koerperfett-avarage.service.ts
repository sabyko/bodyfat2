import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKoerperfettAvarage, NewKoerperfettAvarage } from '../koerperfett-avarage.model';

export type PartialUpdateKoerperfettAvarage = Partial<IKoerperfettAvarage> & Pick<IKoerperfettAvarage, 'id'>;

type RestOf<T extends IKoerperfettAvarage | NewKoerperfettAvarage> = Omit<T, 'datumundZeit'> & {
  datumundZeit?: string | null;
};

export type RestKoerperfettAvarage = RestOf<IKoerperfettAvarage>;

export type NewRestKoerperfettAvarage = RestOf<NewKoerperfettAvarage>;

export type PartialUpdateRestKoerperfettAvarage = RestOf<PartialUpdateKoerperfettAvarage>;

export type EntityResponseType = HttpResponse<IKoerperfettAvarage>;
export type EntityArrayResponseType = HttpResponse<IKoerperfettAvarage[]>;

@Injectable({ providedIn: 'root' })
export class KoerperfettAvarageService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/koerperfett-avarages');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(koerperfettAvarage: NewKoerperfettAvarage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(koerperfettAvarage);
    return this.http
      .post<RestKoerperfettAvarage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(koerperfettAvarage: IKoerperfettAvarage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(koerperfettAvarage);
    return this.http
      .put<RestKoerperfettAvarage>(`${this.resourceUrl}/${this.getKoerperfettAvarageIdentifier(koerperfettAvarage)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(koerperfettAvarage: PartialUpdateKoerperfettAvarage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(koerperfettAvarage);
    return this.http
      .patch<RestKoerperfettAvarage>(`${this.resourceUrl}/${this.getKoerperfettAvarageIdentifier(koerperfettAvarage)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestKoerperfettAvarage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestKoerperfettAvarage[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKoerperfettAvarageIdentifier(koerperfettAvarage: Pick<IKoerperfettAvarage, 'id'>): number {
    return koerperfettAvarage.id;
  }

  compareKoerperfettAvarage(o1: Pick<IKoerperfettAvarage, 'id'> | null, o2: Pick<IKoerperfettAvarage, 'id'> | null): boolean {
    return o1 && o2 ? this.getKoerperfettAvarageIdentifier(o1) === this.getKoerperfettAvarageIdentifier(o2) : o1 === o2;
  }

  addKoerperfettAvarageToCollectionIfMissing<Type extends Pick<IKoerperfettAvarage, 'id'>>(
    koerperfettAvarageCollection: Type[],
    ...koerperfettAvaragesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const koerperfettAvarages: Type[] = koerperfettAvaragesToCheck.filter(isPresent);
    if (koerperfettAvarages.length > 0) {
      const koerperfettAvarageCollectionIdentifiers = koerperfettAvarageCollection.map(
        koerperfettAvarageItem => this.getKoerperfettAvarageIdentifier(koerperfettAvarageItem)!
      );
      const koerperfettAvaragesToAdd = koerperfettAvarages.filter(koerperfettAvarageItem => {
        const koerperfettAvarageIdentifier = this.getKoerperfettAvarageIdentifier(koerperfettAvarageItem);
        if (koerperfettAvarageCollectionIdentifiers.includes(koerperfettAvarageIdentifier)) {
          return false;
        }
        koerperfettAvarageCollectionIdentifiers.push(koerperfettAvarageIdentifier);
        return true;
      });
      return [...koerperfettAvaragesToAdd, ...koerperfettAvarageCollection];
    }
    return koerperfettAvarageCollection;
  }

  protected convertDateFromClient<T extends IKoerperfettAvarage | NewKoerperfettAvarage | PartialUpdateKoerperfettAvarage>(
    koerperfettAvarage: T
  ): RestOf<T> {
    return {
      ...koerperfettAvarage,
      datumundZeit: koerperfettAvarage.datumundZeit?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restKoerperfettAvarage: RestKoerperfettAvarage): IKoerperfettAvarage {
    return {
      ...restKoerperfettAvarage,
      datumundZeit: restKoerperfettAvarage.datumundZeit ? dayjs(restKoerperfettAvarage.datumundZeit) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestKoerperfettAvarage>): HttpResponse<IKoerperfettAvarage> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestKoerperfettAvarage[]>): HttpResponse<IKoerperfettAvarage[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
