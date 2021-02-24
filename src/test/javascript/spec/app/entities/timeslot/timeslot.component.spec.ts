import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VaccnowServiceTestModule } from '../../../test.module';
import { TimeslotComponent } from 'app/entities/timeslot/timeslot.component';
import { TimeslotService } from 'app/entities/timeslot/timeslot.service';
import { Timeslot } from 'app/shared/model/timeslot.model';

describe('Component Tests', () => {
  describe('Timeslot Management Component', () => {
    let comp: TimeslotComponent;
    let fixture: ComponentFixture<TimeslotComponent>;
    let service: TimeslotService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VaccnowServiceTestModule],
        declarations: [TimeslotComponent],
      })
        .overrideTemplate(TimeslotComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TimeslotComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TimeslotService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Timeslot('123')],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.timeslots && comp.timeslots[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
