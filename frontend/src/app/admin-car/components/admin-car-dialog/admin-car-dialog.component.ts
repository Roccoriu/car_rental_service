import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { DynamicDialogRef, DynamicDialogConfig, DialogService } from 'primeng/dynamicdialog';
import { Subject, takeUntil } from 'rxjs';
import { Car, Client, CustomerCreateDto, RentalCreateDto } from 'src/app/core/services/service-clients';

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
    year: new FormControl('', [Validators.required, Validators.pattern("[0-9]{4}")]),
    color: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]),
    rentPriceDay: new FormControl('', [Validators.required, Validators.pattern("[0-9]+")]),
    seats: new FormControl('', [Validators.required, Validators.pattern("[0-9]+")]),
    image: new FormControl('', [Validators.required]),
    automatic: new FormControl(false, [Validators.required]),
  });

  constructor(private config: DynamicDialogConfig, private clientService: Client) { }

  ngOnInit(): void {
    this.car = this.config.data;
  }

  get f(): { [key: string]: AbstractControl } {
    return this.carFormGroup.controls;
  }

  submit(): void {
    this.submitted = true;
    if (this.carFormGroup.invalid) {
      return;
    }

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
