import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISchedule, Schedule } from 'app/shared/model/schedule.model';
import { ScheduleService } from './schedule.service';
import { IBranch } from 'app/shared/model/branch.model';
import { BranchService } from 'app/entities/branch/branch.service';

@Component({
  selector: 'jhi-schedule-update',
  templateUrl: './schedule-update.component.html',
})
export class ScheduleUpdateComponent implements OnInit {
  isSaving = false;
  branches: IBranch[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [null, [Validators.required]],
    isWorkingDay: [],
    branchId: [],
  });

  constructor(
    protected scheduleService: ScheduleService,
    protected branchService: BranchService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schedule }) => {
      this.updateForm(schedule);

      this.branchService.query().subscribe((res: HttpResponse<IBranch[]>) => (this.branches = res.body || []));
    });
  }

  updateForm(schedule: ISchedule): void {
    this.editForm.patchValue({
      id: schedule.id,
      date: schedule.date,
      isWorkingDay: schedule.isWorkingDay,
      branchId: schedule.branchId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const schedule = this.createFromForm();
    if (schedule.id !== undefined) {
      this.subscribeToSaveResponse(this.scheduleService.update(schedule));
    } else {
      this.subscribeToSaveResponse(this.scheduleService.create(schedule));
    }
  }

  private createFromForm(): ISchedule {
    return {
      ...new Schedule(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      isWorkingDay: this.editForm.get(['isWorkingDay'])!.value,
      branchId: this.editForm.get(['branchId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchedule>>): void {
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

  trackById(index: number, item: IBranch): any {
    return item.id;
  }
}
