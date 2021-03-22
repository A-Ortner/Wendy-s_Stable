import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BloodlineComponent } from './bloodline.component';

describe('BloodlineComponent', () => {
  let component: BloodlineComponent;
  let fixture: ComponentFixture<BloodlineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BloodlineComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BloodlineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
