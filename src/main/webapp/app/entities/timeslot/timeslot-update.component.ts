import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITimeslot, Timeslot } from 'app/shared/model/timeslot.model';
import { TimeslotService } from './timeslot.service';
import { ISchedule } from 'app/shared/model/schedule.model';
import { ScheduleService } from 'app/entities/schedule/schedule.service';

@Component({
  selector: 'jhi-timeslot-update',
  templateUrl: './timeslot-update.component.html',
})
export class TimeslotUpdateComponent implements OnInit {
  isSaving = false;
  schedules: ISchedule[] = [];

  editForm = this.fb.group({
    id: [],
    from: [null, [Validators.required]],
    to: [null, [Validators.required]],
    status: [],
    payment: [],
    scheduleId: [],
  });

  constructor(
    protected timeslotService: TimeslotService,
    protected scheduleService: ScheduleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ timeslot }) => {
      if (!timeslot.id) {
        const today = moment().startOf('day');
        timeslot.from = today;
        timeslot.to = today;
      }

      this.updateForm(timeslot);

      this.scheduleService.query().subscribe((res: HttpResponse<ISchedule[]>) => (this.schedules = res.body || []));
    });
  }

  updateForm(timeslot: ITimeslot): void {
    this.editForm.patchValue({
      id: timeslot.id,
      from: timeslot.from ? timeslot.from.format(DATE_TIME_FORMAT) : null,
      to: timeslot.to ? timeslot.to.format(DATE_TIME_FORMAT) : null,
      status: timeslot.status,
      payment: timeslot.payment,
      scheduleId: timeslot.scheduleId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const timeslot = this.createFromForm();
    if (timeslot.id !== undefined) {
      this.subscribeToSaveResponse(this.timeslotService.update(timeslot));
    } else {
      this.subscribeToSaveResponse(this.timeslotService.create(timeslot));
    }
  }

  private createFromForm(): ITimeslot {
    return {
      ...new Timeslot(),
      id: this.editForm.get(['id'])!.value,
      from: this.editForm.get(['from'])!.value ? moment(this.editForm.get(['from'])!.value, DATE_TIME_FORMAT) : undefined,
      to: this.editForm.get(['to'])!.value ? moment(this.editForm.get(['to'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
      payment: this.editForm.get(['payment'])!.value,
      scheduleId: this.editForm.get(['scheduleId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITimeslot>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ISchedule): any {
    return item.id;
  }
}
