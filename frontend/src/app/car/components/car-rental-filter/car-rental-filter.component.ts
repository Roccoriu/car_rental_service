import { Component } from '@angular/core';

@Component({
  selector: 'app-car-rental-filter',
  templateUrl: './car-rental-filter.component.html',
  styleUrl: './car-rental-filter.component.scss'
})
export class CarRentalFilterComponent {
  selectedBrand_1: any;
  rangeValues: number[] = [100, 400];
  selectedColors: any[] = [];
  selectedSizes1: any[] = [];
}
