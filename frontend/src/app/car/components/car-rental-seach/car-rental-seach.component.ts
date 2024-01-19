import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CarGetDto } from 'src/app/core/services/service-clients';

@Component({
  selector: 'app-car-rental-seach',
  templateUrl: './car-rental-seach.component.html',
  styleUrl: './car-rental-seach.component.scss'
})
export class CarRentalSeachComponent {

  @Input() cars: CarGetDto[] = [];
  @Output() filterdCars = new EventEmitter<any[]>();

  constructor() { }

  onSearchChange(event: any) {
    const searchValue = event.target.value.toLowerCase();
    const filterdCars = this.cars.filter(car => {
      return car.brand.toLowerCase().includes(searchValue) ||
        car.model.toLowerCase().includes(searchValue) ||
        car.category.toLowerCase().includes(searchValue) ||
        car.seats.toString().includes(searchValue) ||
        car.rentPriceDay.toString().includes(searchValue) ||
        car.color.toLowerCase().includes(searchValue);
    });
    this.filterdCars.emit(filterdCars);
  }

}
