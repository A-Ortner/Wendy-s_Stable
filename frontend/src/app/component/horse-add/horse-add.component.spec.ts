import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HorseAddComponent } from './horse-add.component';

describe('HorseAddComponent', () => {
  let component: HorseAddComponent;
  let fixture: ComponentFixture<HorseAddComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HorseAddComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HorseAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
