import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VaccnowServiceSharedModule } from 'app/shared/shared.module';
import { ScheduleComponent } from './schedule.component';
import { ScheduleDetailComponent } from './schedule-detail.component';
import { ScheduleUpdateComponent } from './schedule-update.component';
import { ScheduleDeleteDialogComponent } from './schedule-delete-dialog.component';
import { scheduleRoute } from './schedule.route';

@NgModule({
  imports: [VaccnowServiceSharedModule, RouterModule.forChild(scheduleRoute)],
  declarations: [ScheduleComponent, ScheduleDetailComponent, ScheduleUpdateComponent, ScheduleDeleteDialogComponent],
  entryComponents: [ScheduleDeleteDialogComponent],
})
export class VaccnowServiceScheduleModule {}
