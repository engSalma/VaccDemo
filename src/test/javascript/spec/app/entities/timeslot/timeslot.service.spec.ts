import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TimeslotService } from 'app/entities/timeslot/timeslot.service';
import { ITimeslot, Timeslot } from 'app/shared/model/timeslot.model';
import { PaymentMethod } from 'app/shared/model/enumerations/payment-method.model';

describe('Service Tests', () => {
  describe('Timeslot Service', () => {
    let injector: TestBed;
    let service: TimeslotService;
    let httpMock: HttpTestingController;
    let elemDefault: ITimeslot;
    let expectedResult: ITimeslot | ITimeslot[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TimeslotService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Timeslot('ID', currentDate, currentDate, 'AAAAAAA', PaymentMethod.CASH);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            from: currentDate.format(DATE_TIME_FORMAT),
            to: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find('123').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Timeslot', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            from: currentDate.format(DATE_TIME_FORMAT),
            to: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            from: currentDate,
            to: currentDate,
          },
          returnedFromService
        );

        service.create(new Timeslot()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Timeslot', () => {
        const returnedFromService = Object.assign(
          {
            from: currentDate.format(DATE_TIME_FORMAT),
            to: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            payment: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            from: currentDate,
            to: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Timeslot', () => {
        const returnedFromService = Object.assign(
          {
            from: currentDate.format(DATE_TIME_FORMAT),
            to: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            payment: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            from: currentDate,
            to: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Timeslot', () => {
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
