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
  @Input() set dates(dates: any) {
    if(!dates) return;
    this.checkCarAvailability(dates);
  }
  isRentable = true;

  constructor(private dialogService: DialogService) {}


  private checkCarAvailability(dates: any): void {
    const startDate = new Date(dates.startDate);
    const endDate = new Date(dates.endDate);
    if(this.car?.rentals === undefined || this.car?.rentals.length === 0) return;
    for (const rental of this.car.rentals) {
      const rentalStart = new Date(rental.startDate);
      const rentalEnd = new Date(rental.endDate);

      // Check for date overlap
      if (startDate < rentalEnd && endDate > rentalStart) {
        this.isRentable = false;
        break; // Car is not available, no need to check further
      }
    }
  }
  
  rentCar() {
    const ref = this.dialogService.open(CarRentDialogComponent, {
      header: 'Rent Car',
      width: '70%',
      data: this.car
    });
  }

}
