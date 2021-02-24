import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VaccnowServiceTestModule } from '../../../test.module';
import { AppConfigurationDetailComponent } from 'app/entities/app-configuration/app-configuration-detail.component';
import { AppConfiguration } from 'app/shared/model/app-configuration.model';

describe('Component Tests', () => {
  describe('AppConfiguration Management Detail Component', () => {
    let comp: AppConfigurationDetailComponent;
    let fixture: ComponentFixture<AppConfigurationDetailComponent>;
    const route = ({ data: of({ appConfiguration: new AppConfiguration('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VaccnowServiceTestModule],
        declarations: [AppConfigurationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AppConfigurationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AppConfigurationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load appConfiguration on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.appConfiguration).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
