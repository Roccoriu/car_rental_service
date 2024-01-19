import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { Car, CarGetDto, Client } from 'src/app/core/services/service-clients';

@Component({
  selector: 'app-car-rental',
  templateUrl: './car-rental.component.html',
  styleUrl: './car-rental.component.scss'
})
export class CarRentalComponent {

  cars: CarGetDto[] = [];
  filterdCars: CarGetDto[] = [];
  searchedCars: CarGetDto[] = [];
  sortDirection = 'down';
  rangeDates: any[] = [];
  today = new Date();

  constructor(private serviceClient: Client, private messageService: MessageService) { }

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

  checkDateSelection() {
    //check if rangeDates have 2 dates
    if (this.rangeDates[0] == null || this.rangeDates[1] == null) return;
    if (this.rangeDates.length < 2) return;
    if (this.rangeDates[1].getDate() - this.rangeDates[0].getDate() > 7) {
      this.rangeDates = [new Date(), new Date(new Date().setDate(new Date().getDate() + 1))];
      this.messageService.add({ severity: 'warn', summary: 'Warning', detail: 'The retnting period may be a maximum of 7 days'});
    }
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
