import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarRentalComponent } from './views/car-rental/car-rental.component';
import { CarRentalFilterComponent } from './components/car-rental-filter/car-rental-filter.component';
import { CarCardComponent } from './components/car-card/car-card.component';
import { DividerModule } from 'primeng/divider';
import { AccordionModule } from 'primeng/accordion';
import { CheckboxModule } from 'primeng/checkbox';
import { BadgeModule } from 'primeng/badge';
import { SliderModule } from 'primeng/slider';
import { InputNumberModule } from 'primeng/inputnumber';
import { GalleriaModule } from 'primeng/galleria';
import { ButtonModule } from 'primeng/button';
import { ReactiveFormsModule } from '@angular/forms';




@NgModule({
  declarations: [CarRentalComponent, CarRentalFilterComponent, CarCardComponent],
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
    ReactiveFormsModule,
    
  ]
})
export class CarModule { }
