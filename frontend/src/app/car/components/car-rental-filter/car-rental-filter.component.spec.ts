import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarRentalFilterComponent } from './car-rental-filter.component';

describe('CarRentalFilterComponent', () => {
  let component: CarRentalFilterComponent;
  let fixture: ComponentFixture<CarRentalFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarRentalFilterComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CarRentalFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
