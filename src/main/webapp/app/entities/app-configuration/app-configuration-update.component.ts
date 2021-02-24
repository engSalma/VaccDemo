import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAppConfiguration, AppConfiguration } from 'app/shared/model/app-configuration.model';
import { AppConfigurationService } from './app-configuration.service';

@Component({
  selector: 'jhi-app-configuration-update',
  templateUrl: './app-configuration-update.component.html',
})
export class AppConfigurationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required]],
    dataType: [null, [Validators.required]],
    pattern: [],
    value: [null, [Validators.required]],
  });

  constructor(
    protected appConfigurationService: AppConfigurationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appConfiguration }) => {
      this.updateForm(appConfiguration);
    });
  }

  updateForm(appConfiguration: IAppConfiguration): void {
    this.editForm.patchValue({
      id: appConfiguration.id,
      key: appConfiguration.key,
      dataType: appConfiguration.dataType,
      pattern: appConfiguration.pattern,
      value: appConfiguration.value,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const appConfiguration = this.createFromForm();
    if (appConfiguration.id !== undefined) {
      this.subscribeToSaveResponse(this.appConfigurationService.update(appConfiguration));
    } else {
      this.subscribeToSaveResponse(this.appConfigurationService.create(appConfiguration));
    }
  }

  private createFromForm(): IAppConfiguration {
    return {
      ...new AppConfiguration(),
      id: this.editForm.get(['id'])!.value,
      key: this.editForm.get(['key'])!.value,
      dataType: this.editForm.get(['dataType'])!.value,
      pattern: this.editForm.get(['pattern'])!.value,
      value: this.editForm.get(['value'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppConfiguration>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
