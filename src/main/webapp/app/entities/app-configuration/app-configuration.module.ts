import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VaccnowServiceSharedModule } from 'app/shared/shared.module';
import { AppConfigurationComponent } from './app-configuration.component';
import { AppConfigurationDetailComponent } from './app-configuration-detail.component';
import { AppConfigurationUpdateComponent } from './app-configuration-update.component';
import { AppConfigurationDeleteDialogComponent } from './app-configuration-delete-dialog.component';
import { appConfigurationRoute } from './app-configuration.route';

@NgModule({
  imports: [VaccnowServiceSharedModule, RouterModule.forChild(appConfigurationRoute)],
  declarations: [
    AppConfigurationComponent,
    AppConfigurationDetailComponent,
    AppConfigurationUpdateComponent,
    AppConfigurationDeleteDialogComponent,
  ],
  entryComponents: [AppConfigurationDeleteDialogComponent],
})
export class VaccnowServiceAppConfigurationModule {}
