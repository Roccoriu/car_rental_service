import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { DynamicDialogConfig } from 'primeng/dynamicdialog';
import { Client, CustomerCreateDto, RentalCreateDto } from 'src/app/core/services/service-clients';

@Component({
  selector: 'app-car-rent-dialog',
  templateUrl: './car-rent-dialog.component.html',
  styleUrl: './car-rent-dialog.component.scss'
})
export class CarRentDialogComponent {

  submitted: boolean = false;

  car: any;
  minBirthDate: Date = new Date();

  rentFormGroup: FormGroup = new FormGroup({
    firstname: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]),
    lastname: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
    email: new FormControl('', [Validators.required, Validators.email]),
    // regex dd.mm.yyyy
    birthday: new FormControl('', [Validators.required, Validators.pattern("")]),
    startDate: new FormControl('', [Validators.required]),
    endDate: new FormControl('', [Validators.required]),
  });

  constructor(private config: DynamicDialogConfig, private clientService: Client) { }

  ngOnInit(): void {
    this.car = this.config.data;
    this.minBirthDate.setFullYear(this.minBirthDate.getFullYear() - 18);
    console.log(this.minBirthDate);
  }

  get f(): { [key: string]: AbstractControl } {
    return this.rentFormGroup.controls;
  }

  submit(): void {
    this.submitted = true;
    if (this.rentFormGroup.invalid) {
      return;
    }

    let customer = new CustomerCreateDto();
    customer.firstName = this.f.firstname.value;
    customer.lastName = this.f.lastname.value;
    customer.email = this.f.email.value;
    customer.dateOfBirth = this.f.birthday.value;
    this.clientService.postCustomer(customer).subscribe((customer) => {
      console.log(customer);
      let rental = new RentalCreateDto();
      rental.customerId = 1;
      rental.carId = this.car.id;
      rental.startDate = this.f.startDate.value;
      rental.endDate = this.f.endDate.value;
      this.clientService.postRental(rental).subscribe((rental) => {
        console.log(rental);
        this.submitted = false;
        this.rentFormGroup.reset();
      });
    });
  }
}
