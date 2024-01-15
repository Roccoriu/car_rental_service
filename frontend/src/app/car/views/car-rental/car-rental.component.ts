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

  constructor(private serviceClient: Client) { }

  ngOnInit(): void {
    this.serviceClient.getCars().subscribe(cars => {
      this.cars = cars
      this.filterdCars = cars;
      this.searchedCars = cars;
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
