import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { VaccnowServiceSharedModule } from 'app/shared/shared.module';
import { VaccnowServiceCoreModule } from 'app/core/core.module';
import { VaccnowServiceAppRoutingModule } from './app-routing.module';
import { VaccnowServiceHomeModule } from './home/home.module';
import { VaccnowServiceEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    VaccnowServiceSharedModule,
    VaccnowServiceCoreModule,
    VaccnowServiceHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    VaccnowServiceEntityModule,
    VaccnowServiceAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class VaccnowServiceAppModule {}
