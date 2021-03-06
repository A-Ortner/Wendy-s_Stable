import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SportAddComponent } from './sport-add.component';

describe('SportAddComponent', () => {
  let component: SportAddComponent;
  let fixture: ComponentFixture<SportAddComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SportAddComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SportAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
