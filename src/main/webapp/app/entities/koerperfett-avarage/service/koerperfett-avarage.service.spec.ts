import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IKoerperfettAvarage } from '../koerperfett-avarage.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../koerperfett-avarage.test-samples';

import { KoerperfettAvarageService, RestKoerperfettAvarage } from './koerperfett-avarage.service';

const requireRestSample: RestKoerperfettAvarage = {
  ...sampleWithRequiredData,
  datumundZeit: sampleWithRequiredData.datumundZeit?.toJSON(),
};

describe('KoerperfettAvarage Service', () => {
  let service: KoerperfettAvarageService;
  let httpMock: HttpTestingController;
  let expectedResult: IKoerperfettAvarage | IKoerperfettAvarage[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(KoerperfettAvarageService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a KoerperfettAvarage', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const koerperfettAvarage = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(koerperfettAvarage).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a KoerperfettAvarage', () => {
      const koerperfettAvarage = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(koerperfettAvarage).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a KoerperfettAvarage', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of KoerperfettAvarage', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a KoerperfettAvarage', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addKoerperfettAvarageToCollectionIfMissing', () => {
      it('should add a KoerperfettAvarage to an empty array', () => {
        const koerperfettAvarage: IKoerperfettAvarage = sampleWithRequiredData;
        expectedResult = service.addKoerperfettAvarageToCollectionIfMissing([], koerperfettAvarage);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(koerperfettAvarage);
      });

      it('should not add a KoerperfettAvarage to an array that contains it', () => {
        const koerperfettAvarage: IKoerperfettAvarage = sampleWithRequiredData;
        const koerperfettAvarageCollection: IKoerperfettAvarage[] = [
          {
            ...koerperfettAvarage,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addKoerperfettAvarageToCollectionIfMissing(koerperfettAvarageCollection, koerperfettAvarage);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a KoerperfettAvarage to an array that doesn't contain it", () => {
        const koerperfettAvarage: IKoerperfettAvarage = sampleWithRequiredData;
        const koerperfettAvarageCollection: IKoerperfettAvarage[] = [sampleWithPartialData];
        expectedResult = service.addKoerperfettAvarageToCollectionIfMissing(koerperfettAvarageCollection, koerperfettAvarage);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(koerperfettAvarage);
      });

      it('should add only unique KoerperfettAvarage to an array', () => {
        const koerperfettAvarageArray: IKoerperfettAvarage[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const koerperfettAvarageCollection: IKoerperfettAvarage[] = [sampleWithRequiredData];
        expectedResult = service.addKoerperfettAvarageToCollectionIfMissing(koerperfettAvarageCollection, ...koerperfettAvarageArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const koerperfettAvarage: IKoerperfettAvarage = sampleWithRequiredData;
        const koerperfettAvarage2: IKoerperfettAvarage = sampleWithPartialData;
        expectedResult = service.addKoerperfettAvarageToCollectionIfMissing([], koerperfettAvarage, koerperfettAvarage2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(koerperfettAvarage);
        expect(expectedResult).toContain(koerperfettAvarage2);
      });

      it('should accept null and undefined values', () => {
        const koerperfettAvarage: IKoerperfettAvarage = sampleWithRequiredData;
        expectedResult = service.addKoerperfettAvarageToCollectionIfMissing([], null, koerperfettAvarage, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(koerperfettAvarage);
      });

      it('should return initial array if no KoerperfettAvarage is added', () => {
        const koerperfettAvarageCollection: IKoerperfettAvarage[] = [sampleWithRequiredData];
        expectedResult = service.addKoerperfettAvarageToCollectionIfMissing(koerperfettAvarageCollection, undefined, null);
        expect(expectedResult).toEqual(koerperfettAvarageCollection);
      });
    });

    describe('compareKoerperfettAvarage', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareKoerperfettAvarage(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareKoerperfettAvarage(entity1, entity2);
        const compareResult2 = service.compareKoerperfettAvarage(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareKoerperfettAvarage(entity1, entity2);
        const compareResult2 = service.compareKoerperfettAvarage(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareKoerperfettAvarage(entity1, entity2);
        const compareResult2 = service.compareKoerperfettAvarage(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
