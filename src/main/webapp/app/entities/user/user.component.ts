import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUser } from 'app/shared/model/user.model';
import { UserService } from './user.service';
import { UserDeleteDialogComponent } from './user-delete-dialog.component';

@Component({
  selector: 'jhi-user',
  templateUrl: './user.component.html',
})
export class UserComponent implements OnInit, OnDestroy {
  users?: IUser[];
  eventSubscriber?: Subscription;

  constructor(protected userService: UserService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUsers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUser): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUsers(): void {
    this.eventSubscriber = this.eventManager.subscribe('userListModification', () => this.loadAll());
  }

  delete(user: IUser): void {
    const modalRef = this.modalService.open(UserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
  }
}
