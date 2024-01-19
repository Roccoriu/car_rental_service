import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { DynamicDialogRef, DynamicDialogConfig, DialogService } from 'primeng/dynamicdialog';
import { Subject, firstValueFrom, takeUntil } from 'rxjs';
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

  constructor(private http: HttpClient, private config: DynamicDialogConfig, private ref: DynamicDialogRef) { }


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
    this.getImageAsBase64(car.image).then((base64Image: any) => {
      this.carFormGroup.patchValue({
        category: car.category,
        brand: car.brand,
        model: car.model,
        year: new Date(car.year, 0),
        color: car.color,
        rentPriceDay: car.rentPriceDay,
        seats: car.seats,
        image: base64Image,
        automatic: car.automatic
      });
    }).catch((error: any) => {
      console.error('Error fetching image:', error);
    });
  }

getImageAsBase64(url: string): Promise<string> {
    return firstValueFrom(this.http.get("https://joltx-car-rental.s3.eu-central-1.amazonaws.com/" + url, { responseType: 'blob' })).then(blob => {
        return new Promise<string>((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsDataURL(blob);
            reader.onloadend = () => {
                const base64data = reader.result as string;
                resolve(base64data);
            };
            reader.onerror = (error) => {
                reject(error);
            };
        });
    });
}

  submit(): void {
    this.submitted = true;
    if (this.carFormGroup.invalid) {
      return;
    }
    let newCar = new CarCreateUpdateDto();
    newCar.category = this.f.category.value;
    newCar.brand = this.f.brand.value;
    newCar.model = this.f.model.value;
    newCar.year = this.f.year.value.getFullYear();
    newCar.color = this.f.color.value;
    newCar.rentPriceDay = this.f.rentPriceDay.value;
    newCar.seats = this.f.seats.value;
    newCar.imageData = this.f.image.value;
    newCar.automatic = this.f.automatic.value;

    this.ref.close(newCar);

  }

  onSelect(event: { files: File[]; }): void {
    const file: File = event.files[0];
    const reader = new FileReader();

    reader.readAsDataURL(file);
    reader.onload = () => {
      this.carFormGroup.patchValue({
        image: reader.result
      });
    };
  }
}
