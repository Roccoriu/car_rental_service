import { Component, Input } from '@angular/core';
import { DialogService } from 'primeng/dynamicdialog';
import { CarRentDialogComponent } from '../car-rent-dialog/car-rent-dialog.component';

@Component({
  selector: 'app-car-card',
  templateUrl: './car-card.component.html',
  styleUrl: './car-card.component.scss'
})
export class CarCardComponent {
  @Input() car: any;

  constructor(private dialogService: DialogService) {}


  rentCar() {
    const ref = this.dialogService.open(CarRentDialogComponent, {
      header: 'Rent Car',
      width: '70%',
      data: this.car
    });
  }

}
