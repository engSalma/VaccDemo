import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITimeslot, Timeslot } from 'app/shared/model/timeslot.model';
import { TimeslotService } from './timeslot.service';
import { TimeslotComponent } from './timeslot.component';
import { TimeslotDetailComponent } from './timeslot-detail.component';
import { TimeslotUpdateComponent } from './timeslot-update.component';

@Injectable({ providedIn: 'root' })
export class TimeslotResolve implements Resolve<ITimeslot> {
  constructor(private service: TimeslotService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITimeslot> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((timeslot: HttpResponse<Timeslot>) => {
          if (timeslot.body) {
            return of(timeslot.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Timeslot());
  }
}

export const timeslotRoute: Routes = [
  {
    path: '',
    component: TimeslotComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Timeslots',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TimeslotDetailComponent,
    resolve: {
      timeslot: TimeslotResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Timeslots',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TimeslotUpdateComponent,
    resolve: {
      timeslot: TimeslotResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Timeslots',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TimeslotUpdateComponent,
    resolve: {
      timeslot: TimeslotResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Timeslots',
    },
    canActivate: [UserRouteAccessService],
  },
];
