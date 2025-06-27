import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VehicleCalendar } from './vehicle-calendar.component';

describe('VehicleCalendar', () => {
  let component: VehicleCalendar;
  let fixture: ComponentFixture<VehicleCalendar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VehicleCalendar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VehicleCalendar);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
