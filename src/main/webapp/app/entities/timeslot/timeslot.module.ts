import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VaccnowServiceSharedModule } from 'app/shared/shared.module';
import { TimeslotComponent } from './timeslot.component';
import { TimeslotDetailComponent } from './timeslot-detail.component';
import { TimeslotUpdateComponent } from './timeslot-update.component';
import { TimeslotDeleteDialogComponent } from './timeslot-delete-dialog.component';
import { timeslotRoute } from './timeslot.route';

@NgModule({
  imports: [VaccnowServiceSharedModule, RouterModule.forChild(timeslotRoute)],
  declarations: [TimeslotComponent, TimeslotDetailComponent, TimeslotUpdateComponent, TimeslotDeleteDialogComponent],
  entryComponents: [TimeslotDeleteDialogComponent],
})
export class VaccnowServiceTimeslotModule {}
