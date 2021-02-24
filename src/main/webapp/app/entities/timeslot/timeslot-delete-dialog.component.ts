import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITimeslot } from 'app/shared/model/timeslot.model';
import { TimeslotService } from './timeslot.service';

@Component({
  templateUrl: './timeslot-delete-dialog.component.html',
})
export class TimeslotDeleteDialogComponent {
  timeslot?: ITimeslot;

  constructor(protected timeslotService: TimeslotService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.timeslotService.delete(id).subscribe(() => {
      this.eventManager.broadcast('timeslotListModification');
      this.activeModal.close();
    });
  }
}
