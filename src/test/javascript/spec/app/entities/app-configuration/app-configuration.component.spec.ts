import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VaccnowServiceTestModule } from '../../../test.module';
import { AppConfigurationComponent } from 'app/entities/app-configuration/app-configuration.component';
import { AppConfigurationService } from 'app/entities/app-configuration/app-configuration.service';
import { AppConfiguration } from 'app/shared/model/app-configuration.model';

describe('Component Tests', () => {
  describe('AppConfiguration Management Component', () => {
    let comp: AppConfigurationComponent;
    let fixture: ComponentFixture<AppConfigurationComponent>;
    let service: AppConfigurationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VaccnowServiceTestModule],
        declarations: [AppConfigurationComponent],
      })
        .overrideTemplate(AppConfigurationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AppConfigurationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppConfigurationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AppConfiguration('123')],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.appConfigurations && comp.appConfigurations[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
