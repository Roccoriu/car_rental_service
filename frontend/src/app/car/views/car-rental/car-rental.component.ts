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

  constructor(private serviceClient: Client) { }

  ngOnInit(): void {
    this.serviceClient.getCars().subscribe(cars => {
      this.cars = cars
      this.filterdCars = cars;
    });
  }

  filterdCarsChange(cars: Car[]) {
    console.log(cars);
    this.filterdCars = cars;
  }
}
