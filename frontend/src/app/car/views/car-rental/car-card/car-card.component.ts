import { Component, Input } from '@angular/core';
import { DialogService } from 'primeng/dynamicdialog';
import { CarRentDialogComponent } from '../../../components/car-rent-dialog/car-rent-dialog.component';
import { Client, RentalCreateDto } from 'src/app/core/services/service-clients';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-car-card',
  templateUrl: './car-card.component.html',
  styleUrl: './car-card.component.scss'
})
export class CarCardComponent {
  @Input() car: any;
  _dates: any;
  @Input() set dates(dates: any) {
    if (!dates) return;
    this.checkCarAvailability(dates);
    this._dates = dates;
  }
  isRentable = true;

  constructor(private dialogService: DialogService, private clientService: Client, private messageService: MessageService) { }


  private checkCarAvailability(dates: any): void {
    this.isRentable = true;
    const startDate = new Date(dates[0]);
    const endDate = new Date(dates[1]);
    if (this.car?.rentals === undefined || this.car?.rentals.length === 0) return;
    for (const rental of this.car.rentals) {
      const rentalStart = new Date(rental.startDate);
      const rentalEnd = new Date(rental.endDate);

      // Check for date overlap
      if ((startDate >= rentalStart && startDate <= rentalEnd) || // New start is within an existing rental
        (endDate >= rentalStart && endDate <= rentalEnd) || // New end is within an existing rental
        (startDate <= rentalStart && endDate >= rentalEnd)) { // New rental encompasses an existing rental
        this.isRentable = false;
        break; // Car is not available, no need to check further
      }
    }
  }

  rentCar() {
    console.log(this._dates);
    if(this._dates[0] === null || this._dates[1] === null) {
      this.messageService.add({ severity: 'warn', summary: 'Warning', detail: 'Please select a valid date range' });
      return;
    }
    const ref = this.dialogService.open(CarRentDialogComponent, {
      header: 'Rent Car',
      width: '70%',
      dismissableMask: true,
      data: this.car
    });
    ref.onClose.subscribe((rental: RentalCreateDto) => {
      if (!rental) return;
      rental.startDate = new Date(this._dates[0]);
      rental.endDate = new Date(this._dates[1]);
      console.log(rental);
      this.clientService.postRental(rental).subscribe((newRental) => {
        this.car.rentals.push(newRental);
        this.checkCarAvailability(this._dates);
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'The car was successfully rented' });
      });
    });

  }
}
