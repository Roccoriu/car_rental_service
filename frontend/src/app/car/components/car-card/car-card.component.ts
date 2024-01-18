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
    if (!dates) return;
    this.checkCarAvailability(dates);
  }
  isRentable = true;

  constructor(private dialogService: DialogService) { }


  private checkCarAvailability(dates: any): void {
    this.isRentable = true;
    const startDate = new Date(dates[0]);
    const endDate = new Date(dates[1]);
    if (this.car?.rentals === undefined || this.car?.rentals.length === 0) return;
    for (const rental of this.car.rentals) {
      const rentalStart = new Date(rental.startDate);
      const rentalEnd = new Date(rental.endDate);

      // Check for date overlap
      console.log(startDate, endDate, rentalStart, rentalEnd);
      if ((startDate >= rentalStart && startDate <= rentalEnd) || // New start is within an existing rental
        (endDate >= rentalStart && endDate <= rentalEnd) || // New end is within an existing rental
        (startDate <= rentalStart && endDate >= rentalEnd)) { // New rental encompasses an existing rental
        console.log('Car is not available');
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
