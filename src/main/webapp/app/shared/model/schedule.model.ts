import { Moment } from 'moment';
import { ITimeslot } from 'app/shared/model/timeslot.model';

export interface ISchedule {
  id?: string;
  date?: Moment;
  isWorkingDay?: boolean;
  branchName?: string;
  branchId?: string;
  timeslots?: ITimeslot[];
}

export class Schedule implements ISchedule {
  constructor(
    public id?: string,
    public date?: Moment,
    public isWorkingDay?: boolean,
    public branchName?: string,
    public branchId?: string,
    public timeslots?: ITimeslot[]
  ) {
    this.isWorkingDay = this.isWorkingDay || false;
  }
}
