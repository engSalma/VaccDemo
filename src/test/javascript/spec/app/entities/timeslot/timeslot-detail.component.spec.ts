import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VaccnowServiceTestModule } from '../../../test.module';
import { TimeslotDetailComponent } from 'app/entities/timeslot/timeslot-detail.component';
import { Timeslot } from 'app/shared/model/timeslot.model';

describe('Component Tests', () => {
  describe('Timeslot Management Detail Component', () => {
    let comp: TimeslotDetailComponent;
    let fixture: ComponentFixture<TimeslotDetailComponent>;
    const route = ({ data: of({ timeslot: new Timeslot('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VaccnowServiceTestModule],
        declarations: [TimeslotDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TimeslotDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TimeslotDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load timeslot on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.timeslot).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
