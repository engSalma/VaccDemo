import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAppConfiguration } from 'app/shared/model/app-configuration.model';
import { AppConfigurationService } from './app-configuration.service';
import { AppConfigurationDeleteDialogComponent } from './app-configuration-delete-dialog.component';

@Component({
  selector: 'jhi-app-configuration',
  templateUrl: './app-configuration.component.html',
})
export class AppConfigurationComponent implements OnInit, OnDestroy {
  appConfigurations?: IAppConfiguration[];
  eventSubscriber?: Subscription;

  constructor(
    protected appConfigurationService: AppConfigurationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.appConfigurationService.query().subscribe((res: HttpResponse<IAppConfiguration[]>) => (this.appConfigurations = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAppConfigurations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAppConfiguration): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAppConfigurations(): void {
    this.eventSubscriber = this.eventManager.subscribe('appConfigurationListModification', () => this.loadAll());
  }

  delete(appConfiguration: IAppConfiguration): void {
    const modalRef = this.modalService.open(AppConfigurationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.appConfiguration = appConfiguration;
  }
}
