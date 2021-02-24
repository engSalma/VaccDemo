import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      /*{
        path: 'user',
        loadChildren: () => import('./user/user.module').then(m => m.VaccnowServiceUserModule),
      },*/
      {
        path: 'branch',
        loadChildren: () => import('./branch/branch.module').then(m => m.VaccnowServiceBranchModule),
      },
      {
        path: 'app-configuration',
        loadChildren: () => import('./app-configuration/app-configuration.module').then(m => m.VaccnowServiceAppConfigurationModule),
      },
      {
        path: 'payment',
        loadChildren: () => import('./payment/payment.module').then(m => m.VaccnowServicePaymentModule),
      },
      {
        path: 'schedule',
        loadChildren: () => import('./schedule/schedule.module').then(m => m.VaccnowServiceScheduleModule),
      },
      {
        path: 'timeslot',
        loadChildren: () => import('./timeslot/timeslot.module').then(m => m.VaccnowServiceTimeslotModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class VaccnowServiceEntityModule {}
