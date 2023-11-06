import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IKoerperfett } from '../koerperfett.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../koerperfett.test-samples';

import { KoerperfettService, RestKoerperfett } from './koerperfett.service';

const requireRestSample: RestKoerperfett = {
  ...sampleWithRequiredData,
  datumundZeit: sampleWithRequiredData.datumundZeit?.toJSON(),
};

describe('Koerperfett Service', () => {
  let service: KoerperfettService;
  let httpMock: HttpTestingController;
  let expectedResult: IKoerperfett | IKoerperfett[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(KoerperfettService);
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

    it('should create a Koerperfett', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const koerperfett = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(koerperfett).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Koerperfett', () => {
      const koerperfett = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(koerperfett).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Koerperfett', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Koerperfett', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Koerperfett', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addKoerperfettToCollectionIfMissing', () => {
      it('should add a Koerperfett to an empty array', () => {
        const koerperfett: IKoerperfett = sampleWithRequiredData;
        expectedResult = service.addKoerperfettToCollectionIfMissing([], koerperfett);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(koerperfett);
      });

      it('should not add a Koerperfett to an array that contains it', () => {
        const koerperfett: IKoerperfett = sampleWithRequiredData;
        const koerperfettCollection: IKoerperfett[] = [
          {
            ...koerperfett,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addKoerperfettToCollectionIfMissing(koerperfettCollection, koerperfett);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Koerperfett to an array that doesn't contain it", () => {
        const koerperfett: IKoerperfett = sampleWithRequiredData;
        const koerperfettCollection: IKoerperfett[] = [sampleWithPartialData];
        expectedResult = service.addKoerperfettToCollectionIfMissing(koerperfettCollection, koerperfett);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(koerperfett);
      });

      it('should add only unique Koerperfett to an array', () => {
        const koerperfettArray: IKoerperfett[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const koerperfettCollection: IKoerperfett[] = [sampleWithRequiredData];
        expectedResult = service.addKoerperfettToCollectionIfMissing(koerperfettCollection, ...koerperfettArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const koerperfett: IKoerperfett = sampleWithRequiredData;
        const koerperfett2: IKoerperfett = sampleWithPartialData;
        expectedResult = service.addKoerperfettToCollectionIfMissing([], koerperfett, koerperfett2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(koerperfett);
        expect(expectedResult).toContain(koerperfett2);
      });

      it('should accept null and undefined values', () => {
        const koerperfett: IKoerperfett = sampleWithRequiredData;
        expectedResult = service.addKoerperfettToCollectionIfMissing([], null, koerperfett, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(koerperfett);
      });

      it('should return initial array if no Koerperfett is added', () => {
        const koerperfettCollection: IKoerperfett[] = [sampleWithRequiredData];
        expectedResult = service.addKoerperfettToCollectionIfMissing(koerperfettCollection, undefined, null);
        expect(expectedResult).toEqual(koerperfettCollection);
      });
    });

    describe('compareKoerperfett', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareKoerperfett(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareKoerperfett(entity1, entity2);
        const compareResult2 = service.compareKoerperfett(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareKoerperfett(entity1, entity2);
        const compareResult2 = service.compareKoerperfett(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareKoerperfett(entity1, entity2);
        const compareResult2 = service.compareKoerperfett(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
