import { Moment } from 'moment';
import { PaymentMethod } from 'app/shared/model/enumerations/payment-method.model';

export interface ITimeslot {
  id?: string;
  from?: Moment;
  to?: Moment;
  status?: string;
  payment?: PaymentMethod;
  scheduleDate?: string;
  scheduleId?: string;
}

export class Timeslot implements ITimeslot {
  constructor(
    public id?: string,
    public from?: Moment,
    public to?: Moment,
    public status?: string,
    public payment?: PaymentMethod,
    public scheduleDate?: string,
    public scheduleId?: string
  ) {}
}
