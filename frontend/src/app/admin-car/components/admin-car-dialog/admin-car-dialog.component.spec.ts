import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCarDialogComponent } from './admin-car-dialog.component';

describe('AdminCarDialogComponent', () => {
  let component: AdminCarDialogComponent;
  let fixture: ComponentFixture<AdminCarDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminCarDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminCarDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
