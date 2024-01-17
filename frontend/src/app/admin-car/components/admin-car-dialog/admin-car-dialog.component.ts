import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { DynamicDialogRef, DynamicDialogConfig, DialogService } from 'primeng/dynamicdialog';
import { Subject, takeUntil } from 'rxjs';
import { Car, CarCreateUpdateDto, Client, CustomerCreateDto, RentalCreateDto } from 'src/app/core/services/service-clients';

@Component({
  selector: 'app-admin-car-dialog',
  templateUrl: './admin-car-dialog.component.html',
  styleUrl: './admin-car-dialog.component.scss'
})
export class AdminCarDialogComponent {

  submitted: boolean = false;

  car: Car | undefined;

  carFormGroup: FormGroup = new FormGroup({
    category: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]),
    brand: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]),
    model: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]),
    // regex dd.mm.yyyy
    year: new FormControl('', [Validators.required]),
    color: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]),
    rentPriceDay: new FormControl('', [Validators.required, Validators.pattern("[0-9]+")]),
    seats: new FormControl('', [Validators.required, Validators.pattern("[0-9]+")]),
    image: new FormControl('', [Validators.required]),
    automatic: new FormControl(false, [Validators.required]),
  });

  constructor(private config: DynamicDialogConfig, private ref: DynamicDialogRef) { }


  ngOnInit(): void {
    this.car = this.config.data;
    if (this.car) {
      this.patchForm(this.car);
    }
  }

  get f(): { [key: string]: AbstractControl } {
    return this.carFormGroup.controls;
  }

  patchForm(car: Car): void {
    this.carFormGroup.patchValue({
      category: car.category,
      brand: car.brand,
      model: car.model,
      year: new Date(car.year, 0),
      color: car.color,
      rentPriceDay: car.rentPriceDay,
      seats: car.seats,
      image: car.image,
      automatic: car.automatic
    });
  }


  submit(): void {
    console.log(this.carFormGroup);
    this.submitted = true;
    if (this.carFormGroup.invalid) {
      return;
    }
    console.log(this.f.category.value);
    let newCar = new CarCreateUpdateDto();
    newCar.category = this.f.category.value;
    newCar.brand = this.f.brand.value;
    newCar.model = this.f.model.value;
    newCar.year = this.f.year.value;
    newCar.color = this.f.color.value;
    newCar.rentPriceDay = this.f.rentPriceDay.value;
    newCar.seats = this.f.seats.value;
    newCar.image = this.f.image.value;
    newCar.automatic = this.f.automatic.value;
    console.log(newCar);

    this.ref.close(newCar);

  }

  onSelect(event: { files: File[]; }): void {
    const file: File = event.files[0];
    const reader = new FileReader();

    reader.readAsDataURL(file);
    reader.onload = () => {
      console.log(reader.result);
      this.carFormGroup.patchValue({
        image: reader.result
      });
    };
  }
}
