import { Component } from '@angular/core';
import { ConfirmationService } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { Car, CarCreateUpdateDto, Client } from 'src/app/core/services/service-clients';
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

    ref.onClose.subscribe((newCar: CarCreateUpdateDto) => {
      if (newCar) {
        this.clientService.postCar(newCar).subscribe((newCar) => {
          this.cars.push(newCar);
        });
      }
    });
  }

  openEditCarDialog(car: Car) {
    const ref = this.dialogService.open(AdminCarDialogComponent, {
      header: 'Edit Car',
      width: '70%',
      data: car
    });

    ref.onClose.subscribe((newCar: CarCreateUpdateDto) => {
      if (newCar) {
        this.clientService.putCar(car.id, newCar).subscribe((newCar) => {
          this.cars = this.cars.map(c => {
            if (c.id === newCar.id) {
              return newCar;
            }
            return c;
          });
        });
      }
    });

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
