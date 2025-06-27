import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CalendarEvent, CalendarView, CalendarModule } from 'angular-calendar';
import { startOfDay } from 'date-fns';

@Component({
  selector: 'app-vehicle-calendar',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    CalendarModule
  ],
  templateUrl: './vehicle-calendar.component.html',
  styleUrls: ['./vehicle-calendar.component.scss']
})
export class VehicleCalendarComponent implements OnInit {
  private http = inject(HttpClient);

  view: CalendarView = CalendarView.Month;
  viewDate: Date = new Date();
  events: CalendarEvent[] = [];
  loading = false;

  ngOnInit(): void {
    this.loadReservations();
  }

  loadReservations(): void {
    this.loading = true;
    this.http.get<any[]>('/api/reservations')  // Replace with actual endpoint
      .subscribe({
        next: reservations => {
          this.events = reservations.map(r => ({
            start: startOfDay(new Date(r.startDate)),
            end: startOfDay(new Date(r.endDate)),
            title: `Vehicle ${r.vehicleId} Reserved`,
            allDay: true,
            color: { primary: '#ad2121', secondary: '#FAE3E3' }
          }));
          this.loading = false;
        },
        error: () => {
          this.loading = false;
        }
      });
  }
}
