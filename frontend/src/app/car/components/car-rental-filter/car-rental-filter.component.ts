import { Component } from '@angular/core';

@Component({
  selector: 'app-car-rental-filter',
  templateUrl: './car-rental-filter.component.html',
  styleUrl: './car-rental-filter.component.scss'
})
export class CarRentalFilterComponent {
  selectedBrands: any[] = [];
  possibleBrands: any[] = [{name: "Toyota", amount: 1}, {name: "Honda", amount: 1}];
  rangeValues: number[] = [10, 100];
  selectedColors: any[] = [];
  selectedSizes1: any[] = [];
}
