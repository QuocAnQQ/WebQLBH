import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddReturnOrderComponent } from './add-return-order.component';

describe('AddReturnOrderComponent', () => {
  let component: AddReturnOrderComponent;
  let fixture: ComponentFixture<AddReturnOrderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddReturnOrderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddReturnOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
