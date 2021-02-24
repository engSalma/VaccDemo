import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAppConfiguration, AppConfiguration } from 'app/shared/model/app-configuration.model';
import { AppConfigurationService } from './app-configuration.service';
import { AppConfigurationComponent } from './app-configuration.component';
import { AppConfigurationDetailComponent } from './app-configuration-detail.component';
import { AppConfigurationUpdateComponent } from './app-configuration-update.component';

@Injectable({ providedIn: 'root' })
export class AppConfigurationResolve implements Resolve<IAppConfiguration> {
  constructor(private service: AppConfigurationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAppConfiguration> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((appConfiguration: HttpResponse<AppConfiguration>) => {
          if (appConfiguration.body) {
            return of(appConfiguration.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AppConfiguration());
  }
}

export const appConfigurationRoute: Routes = [
  {
    path: '',
    component: AppConfigurationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AppConfigurations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AppConfigurationDetailComponent,
    resolve: {
      appConfiguration: AppConfigurationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AppConfigurations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AppConfigurationUpdateComponent,
    resolve: {
      appConfiguration: AppConfigurationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AppConfigurations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AppConfigurationUpdateComponent,
    resolve: {
      appConfiguration: AppConfigurationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AppConfigurations',
    },
    canActivate: [UserRouteAccessService],
  },
];
