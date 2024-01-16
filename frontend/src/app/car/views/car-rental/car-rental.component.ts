import { Component } from '@angular/core';
import { Car, Client } from 'src/app/core/services/service-clients';

@Component({
  selector: 'app-car-rental',
  templateUrl: './car-rental.component.html',
  styleUrl: './car-rental.component.scss'
})
export class CarRentalComponent {

  cars: Car[] = [];
  filterdCars: Car[] = [];
  searchedCars: Car[] = [];
  sortDirection = 'down';
  rangeDates: any[] = [];

  constructor(private serviceClient: Client) { }

  ngOnInit(): void {
    this.rangeDates = [new Date(), new Date(new Date().setDate(new Date().getDate() + 1))];
    this.serviceClient.getCars().subscribe(cars => {
      //sort cars by price descending
      cars.sort((a, b) => b.rentPriceDay - a.rentPriceDay);
      this.cars = cars
      this.filterdCars = cars;
      this.searchedCars = cars;
    });
  }

  sortList() {
    this.sortDirection = this.sortDirection === 'down' ? 'up' : 'down';
    // Additional sorting logic here
    this.filterdCars.sort((a, b) => {
      if (this.sortDirection === 'up') {
        return a.rentPriceDay - b.rentPriceDay;
      } else {
        return b.rentPriceDay - a.rentPriceDay;
      }
    });
}


  filterdCarsChange(cars: Car[]) {
    this.filterdCars = cars;
    this.searchedCars = cars;
  }

  searchCars(cars: Car[]) {
    this.searchedCars = cars;
  }
}
