import { Component } from '@angular/core';

@Component({
  selector: 'app-car-rental',
  templateUrl: './car-rental.component.html',
  styleUrl: './car-rental.component.scss'
})
export class CarRentalComponent {

  cars: any[] = [
    {
      id: 2,
      category: "Compact Car",
      brand: "Honda",
      model: "Civic",
      year: 2021,
      color: "Blue",
      rentPriceDay: 90.0,
      isAutomatic: false,
      seats: 5,
      image: "assets/images/cars/c2.jpg"
    },
    {
      id: 1,
      category: "Compact Car",
      brand: "Toyota",
      model: "Camry",
      year: 2022,
      color: "Red",
      rentPriceDay: 100.0,
      isAutomatic: true,
      seats: 5,
      image: "assets/images/cars/c1.jpg",
    }
  ];
  
}
