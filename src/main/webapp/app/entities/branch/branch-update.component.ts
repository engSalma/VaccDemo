import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBranch, Branch } from 'app/shared/model/branch.model';
import { BranchService } from './branch.service';

@Component({
  selector: 'jhi-branch-update',
  templateUrl: './branch-update.component.html',
})
export class BranchUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    address: [],
    vaccineAllowed: [],
    vaccineStock: [],
  });

  constructor(protected branchService: BranchService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ branch }) => {
      this.updateForm(branch);
    });
  }

  updateForm(branch: IBranch): void {
    this.editForm.patchValue({
      id: branch.id,
      name: branch.name,
      address: branch.address,
      vaccineAllowed: branch.vaccineAllowed,
      vaccineStock: branch.vaccineStock,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const branch = this.createFromForm();
    if (branch.id !== undefined) {
      this.subscribeToSaveResponse(this.branchService.update(branch));
    } else {
      this.subscribeToSaveResponse(this.branchService.create(branch));
    }
  }

  private createFromForm(): IBranch {
    return {
      ...new Branch(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      address: this.editForm.get(['address'])!.value,
      vaccineAllowed: this.editForm.get(['vaccineAllowed'])!.value,
      vaccineStock: this.editForm.get(['vaccineStock'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBranch>>): void {
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
