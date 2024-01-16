import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCarListComponent } from './admin-car-list.component';

describe('AdminCarListComponent', () => {
  let component: AdminCarListComponent;
  let fixture: ComponentFixture<AdminCarListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminCarListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminCarListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
