import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AppConfigurationService } from 'app/entities/app-configuration/app-configuration.service';
import { IAppConfiguration, AppConfiguration } from 'app/shared/model/app-configuration.model';
import { DataType } from 'app/shared/model/enumerations/data-type.model';

describe('Service Tests', () => {
  describe('AppConfiguration Service', () => {
    let injector: TestBed;
    let service: AppConfigurationService;
    let httpMock: HttpTestingController;
    let elemDefault: IAppConfiguration;
    let expectedResult: IAppConfiguration | IAppConfiguration[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AppConfigurationService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AppConfiguration('ID', 'AAAAAAA', DataType.STRING, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find('123').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AppConfiguration', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AppConfiguration()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AppConfiguration', () => {
        const returnedFromService = Object.assign(
          {
            key: 'BBBBBB',
            dataType: 'BBBBBB',
            pattern: 'BBBBBB',
            value: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AppConfiguration', () => {
        const returnedFromService = Object.assign(
          {
            key: 'BBBBBB',
            dataType: 'BBBBBB',
            pattern: 'BBBBBB',
            value: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AppConfiguration', () => {
        service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
