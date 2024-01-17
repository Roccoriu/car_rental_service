import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LayoutModule } from './layout/layout.module';
import { CarModule } from './car/car.module';
import { HttpClientModule } from '@angular/common/http';
import { AdminCarModule } from './admin-car/admin-car.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LayoutModule,
    CarModule,
    HttpClientModule,
    AdminCarModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
