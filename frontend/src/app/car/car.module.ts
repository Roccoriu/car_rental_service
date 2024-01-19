import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarRentalComponent } from './views/car-rental/car-rental.component';
import { CarRentalFilterComponent } from './components/car-rental-filter/car-rental-filter.component';
import { CarCardComponent } from './views/car-rental/car-card/car-card.component';
import { DividerModule } from 'primeng/divider';
import { AccordionModule } from 'primeng/accordion';
import { CheckboxModule } from 'primeng/checkbox';
import { BadgeModule } from 'primeng/badge';
import { SliderModule } from 'primeng/slider';
import { InputNumberModule } from 'primeng/inputnumber';
import { GalleriaModule } from 'primeng/galleria';
import { ButtonModule } from 'primeng/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DialogService, DynamicDialogModule } from 'primeng/dynamicdialog';
import { CalendarModule } from 'primeng/calendar';
import { CarRentDialogComponent } from './components/car-rent-dialog/car-rent-dialog.component';
import { InputSwitchModule } from 'primeng/inputswitch';
import { CarRentalSeachComponent } from './components/car-rental-seach/car-rental-seach.component';






@NgModule({
  declarations: [CarRentalComponent, CarRentalFilterComponent, CarCardComponent, CarRentDialogComponent, CarRentalSeachComponent],
  imports: [
    CommonModule,
    DividerModule,
    AccordionModule,
    CheckboxModule,
    BadgeModule,
    SliderModule,
    InputNumberModule,
    GalleriaModule,
    ButtonModule,
    FormsModule,
    InputTextModule,
    BrowserAnimationsModule,
    DynamicDialogModule,
    CalendarModule,
    ReactiveFormsModule,
    InputSwitchModule,
  ],
  providers: [DialogService]
})
export class CarModule { }
