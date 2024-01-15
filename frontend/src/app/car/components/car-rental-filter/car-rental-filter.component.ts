import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Car } from 'src/app/core/services/service-clients';

@Component({
  selector: 'app-car-rental-filter',
  templateUrl: './car-rental-filter.component.html',
  styleUrl: './car-rental-filter.component.scss'
})
export class CarRentalFilterComponent {
  selectedBrands: any[] = [];
  possibleBrands = new Map()
  rangeValues: number[] = [];
  maxPrice: number = 1;
  minPrice: number = 0;
  selectedColors: any[] = [];
  possibleCategories = new Map()
  selectedCategories: any[] = [];
  maxSeats: number = 1;
  minSeats: number = 0;
  selectetminSeats: number = 0;
  isAutomatic: boolean = false;

  private _cars: Car[] = [];

  @Input() set cars(cars: any[]) {
    this._cars = cars;
    if (cars && cars.length > 0) {
      this.initPossibleBrands();
      this.initPriceRange();
      this.initPossibleCategories();
      this.initPossibleSeats();
    }
  }

  get cars(): any[] {
    return this._cars;
  }

  @Output() filterdCars = new EventEmitter<Car[]>();

  constructor() { }

  ngOnInit(): void {
  }

  initPossibleBrands() {
    this.cars.forEach(car => {
      if (this.possibleBrands.has(car.brand)) {
        this.possibleBrands.set(car.brand, this.possibleBrands.get(car.brand) + 1);
      } else {
        this.possibleBrands.set(car.brand, 1);
      }
    });
  }

  initPossibleCategories() {
    this.cars.forEach(car => {
      if (this.possibleCategories.has(car.category)) {
        this.possibleCategories.set(car.category, this.possibleCategories.get(car.category) + 1);
      } else {
        this.possibleCategories.set(car.category, 1);
      }
    });
  }

  initPriceRange() {
    this.maxPrice = this.cars[0].rentPriceDay;
    this.minPrice = this.cars[0].rentPriceDay;
    this.cars.forEach(car => {
      if (car.rentPriceDay > this.maxPrice) {
        this.maxPrice = car.rentPriceDay;
      }
      if (car.rentPriceDay < this.minPrice) {
        this.minPrice = car.rentPriceDay;
      }
    });
    this.rangeValues = [this.minPrice, this.maxPrice];
  }



  initPossibleSeats() {
    this.maxSeats = this.cars[0].seats;
    this.minSeats = this.cars[0].seats;
    this.cars.forEach(car => {
      if (car.seats > this.maxSeats) {
        this.maxSeats = car.seats;
      }
      if (car.seats < this.minSeats) {
        this.minSeats = car.seats;
      }
      this.selectetminSeats = this.minSeats;
    });
  }

  filterCars() {
    let filteredCars = this.cars.filter(car => {
      if(this.selectedCategories.length > 0 && !this.selectedCategories.includes(car.category)) {
        return false;
      }
      if (this.selectedBrands.length > 0 && !this.selectedBrands.includes(car.brand)) {
        return false;
      }
      if (this.selectedColors.length > 0 && !this.selectedColors.includes(car.color)) {
        return false;
      }
      if (this.selectetminSeats > car.seats) {
        return false;
      }
      if (this.isAutomatic && !car.automatic) {
        return false;
      }
      if (this.rangeValues[0] > car.rentPriceDay || this.rangeValues[1] < car.rentPriceDay) {
        return false;
      }
      return true;
    });
    this.filterdCars.emit(filteredCars);
  }

}
