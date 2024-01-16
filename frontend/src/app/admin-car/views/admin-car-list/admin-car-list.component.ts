import { Component } from '@angular/core';
import { ConfirmationService } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { Car, Client } from 'src/app/core/services/service-clients';

@Component({
  selector: 'app-admin-car-list',
  templateUrl: './admin-car-list.component.html',
  styleUrl: './admin-car-list.component.scss'
})
export class AdminCarListComponent {

  cars: Car[] = [];

  constructor(private clientService: Client, private confirmationService: ConfirmationService, private dialogService: DialogService) { }

  ngOnInit() {
    this.clientService.getCars().subscribe(cars => {
      this.cars = cars;
    });
  }

  openCreateCarDialog() {

  }

  openEditCarDialog(car: Car) {

  }

  deleteCar(car: Car) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete this car?',
      accept: () => {
        this.clientService.deleteCar(car.id).subscribe(() => {
          this.cars = this.cars.filter(c => c.id !== car.id);
        });
      }
    });
  }

}
