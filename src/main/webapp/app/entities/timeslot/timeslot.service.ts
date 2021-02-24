import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITimeslot } from 'app/shared/model/timeslot.model';

type EntityResponseType = HttpResponse<ITimeslot>;
type EntityArrayResponseType = HttpResponse<ITimeslot[]>;

@Injectable({ providedIn: 'root' })
export class TimeslotService {
  public resourceUrl = SERVER_API_URL + 'api/timeslots';

  constructor(protected http: HttpClient) {}

  create(timeslot: ITimeslot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(timeslot);
    return this.http
      .post<ITimeslot>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(timeslot: ITimeslot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(timeslot);
    return this.http
      .put<ITimeslot>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ITimeslot>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITimeslot[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(timeslot: ITimeslot): ITimeslot {
    const copy: ITimeslot = Object.assign({}, timeslot, {
      from: timeslot.from && timeslot.from.isValid() ? timeslot.from.toJSON() : undefined,
      to: timeslot.to && timeslot.to.isValid() ? timeslot.to.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.from = res.body.from ? moment(res.body.from) : undefined;
      res.body.to = res.body.to ? moment(res.body.to) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((timeslot: ITimeslot) => {
        timeslot.from = timeslot.from ? moment(timeslot.from) : undefined;
        timeslot.to = timeslot.to ? moment(timeslot.to) : undefined;
      });
    }
    return res;
  }
}
