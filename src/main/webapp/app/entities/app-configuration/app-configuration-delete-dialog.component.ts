import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppConfiguration } from 'app/shared/model/app-configuration.model';
import { AppConfigurationService } from './app-configuration.service';

@Component({
  templateUrl: './app-configuration-delete-dialog.component.html',
})
export class AppConfigurationDeleteDialogComponent {
  appConfiguration?: IAppConfiguration;

  constructor(
    protected appConfigurationService: AppConfigurationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.appConfigurationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('appConfigurationListModification');
      this.activeModal.close();
    });
  }
}
