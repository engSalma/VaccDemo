import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VaccnowServiceTestModule } from '../../../test.module';
import { TimeslotUpdateComponent } from 'app/entities/timeslot/timeslot-update.component';
import { TimeslotService } from 'app/entities/timeslot/timeslot.service';
import { Timeslot } from 'app/shared/model/timeslot.model';

describe('Component Tests', () => {
  describe('Timeslot Management Update Component', () => {
    let comp: TimeslotUpdateComponent;
    let fixture: ComponentFixture<TimeslotUpdateComponent>;
    let service: TimeslotService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VaccnowServiceTestModule],
        declarations: [TimeslotUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TimeslotUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TimeslotUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TimeslotService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Timeslot('123');
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
        const entity = new Timeslot();
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
