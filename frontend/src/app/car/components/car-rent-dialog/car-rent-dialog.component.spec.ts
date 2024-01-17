import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarRentDialogComponent } from './car-rent-dialog.component';

describe('CarRentDialogComponent', () => {
  let component: CarRentDialogComponent;
  let fixture: ComponentFixture<CarRentDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarRentDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CarRentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
