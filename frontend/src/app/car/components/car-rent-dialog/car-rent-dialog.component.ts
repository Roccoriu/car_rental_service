import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { CarGetDto, Client, CustomerCreateDto, RentalCreateDto } from 'src/app/core/services/service-clients';

@Component({
  selector: 'app-car-rent-dialog',
  templateUrl: './car-rent-dialog.component.html',
  styleUrl: './car-rent-dialog.component.scss'
})
export class CarRentDialogComponent {

  submitted: boolean = false;

  car: CarGetDto | undefined;
  minBirthDate: Date = new Date(new Date().setFullYear(new Date().getFullYear() - 18));
  rentFormGroup: FormGroup = new FormGroup({
    firstname: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]),
    lastname: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
    email: new FormControl('', [Validators.required, Validators.email]),
    birthday: new FormControl('', [Validators.required, Validators.pattern("")]),
  });

  constructor(private config: DynamicDialogConfig, private clientService: Client, private ref: DynamicDialogRef, private messageService: MessageService) { }

  ngOnInit(): void {
    this.car = this.config.data;

  }

  get f(): { [key: string]: AbstractControl } {
    return this.rentFormGroup.controls;
  }

  submit(): void {
    this.submitted = true;
    if (this.rentFormGroup.invalid) {
      return;
    }
    //check if customer is 18 or older
    if (this.f.birthday.value > this.minBirthDate) {
      this.messageService.add({ severity: 'error', summary: 'Denied', detail: 'You must be 18 or older to rent a car' });
      this.ref.close();
      return;
    }
    let customer = new CustomerCreateDto();
    customer.firstName = this.f.firstname.value;
    customer.lastName = this.f.lastname.value;
    customer.email = this.f.email.value;
    customer.dateOfBirth = this.f.birthday.value;
    this.clientService.postCustomer(customer).subscribe((customer) => {
      let rental = new RentalCreateDto();
      rental.customerId = customer.id;
      rental.carId = this.car!.id;
      this.ref.close(rental);
    });
  }
}
