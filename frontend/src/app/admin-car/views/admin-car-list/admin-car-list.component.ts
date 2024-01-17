import { Component } from '@angular/core';
import { ConfirmationService } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { Car, Client } from 'src/app/core/services/service-clients';
import { AdminCarDialogComponent } from '../../components/admin-car-dialog/admin-car-dialog.component';

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
    const ref = this.dialogService.open(AdminCarDialogComponent, {
      header: 'Create Car',
      width: '70%',
      data: null
    });

    ref.onClose.subscribe((car: Car) => {
      if (car) {
        this.clientService.postCar(car).subscribe((car) => {
          this.cars.push(car);
        });
      }
    });
  }

  openEditCarDialog(car: Car) {

  }

  deleteCar(car: Car) {
    console.log(car);
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete this car?',
      header: 'Delete Confirmation',
      accept: () => {
        this.clientService.deleteCar(car.id).subscribe(() => {
          this.cars = this.cars.filter(c => c.id !== car.id);
        });
      }
    });
  }

}
