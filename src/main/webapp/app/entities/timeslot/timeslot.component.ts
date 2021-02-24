import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITimeslot } from 'app/shared/model/timeslot.model';
import { TimeslotService } from './timeslot.service';
import { TimeslotDeleteDialogComponent } from './timeslot-delete-dialog.component';

@Component({
  selector: 'jhi-timeslot',
  templateUrl: './timeslot.component.html',
})
export class TimeslotComponent implements OnInit, OnDestroy {
  timeslots?: ITimeslot[];
  eventSubscriber?: Subscription;

  constructor(protected timeslotService: TimeslotService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.timeslotService.query().subscribe((res: HttpResponse<ITimeslot[]>) => (this.timeslots = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTimeslots();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITimeslot): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTimeslots(): void {
    this.eventSubscriber = this.eventManager.subscribe('timeslotListModification', () => this.loadAll());
  }

  delete(timeslot: ITimeslot): void {
    const modalRef = this.modalService.open(TimeslotDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.timeslot = timeslot;
  }
}
