import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VaccnowServiceTestModule } from '../../../test.module';
import { AppConfigurationUpdateComponent } from 'app/entities/app-configuration/app-configuration-update.component';
import { AppConfigurationService } from 'app/entities/app-configuration/app-configuration.service';
import { AppConfiguration } from 'app/shared/model/app-configuration.model';

describe('Component Tests', () => {
  describe('AppConfiguration Management Update Component', () => {
    let comp: AppConfigurationUpdateComponent;
    let fixture: ComponentFixture<AppConfigurationUpdateComponent>;
    let service: AppConfigurationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VaccnowServiceTestModule],
        declarations: [AppConfigurationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AppConfigurationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AppConfigurationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppConfigurationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AppConfiguration('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AppConfiguration();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
