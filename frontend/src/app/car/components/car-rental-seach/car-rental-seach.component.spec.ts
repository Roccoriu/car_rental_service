import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarRentalSeachComponent } from './car-rental-seach.component';

describe('CarRentalSeachComponent', () => {
  let component: CarRentalSeachComponent;
  let fixture: ComponentFixture<CarRentalSeachComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarRentalSeachComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CarRentalSeachComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
