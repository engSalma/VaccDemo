import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITimeslot } from 'app/shared/model/timeslot.model';

@Component({
  selector: 'jhi-timeslot-detail',
  templateUrl: './timeslot-detail.component.html',
})
export class TimeslotDetailComponent implements OnInit {
  timeslot: ITimeslot | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ timeslot }) => (this.timeslot = timeslot));
  }

  previousState(): void {
    window.history.back();
  }
}
