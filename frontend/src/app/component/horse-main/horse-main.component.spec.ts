import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HorseMainComponent } from './horse-main.component';

describe('HorseMainComponent', () => {
  let component: HorseMainComponent;
  let fixture: ComponentFixture<HorseMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HorseMainComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HorseMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
