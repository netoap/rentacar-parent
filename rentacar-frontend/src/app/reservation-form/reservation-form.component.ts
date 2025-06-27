import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Vehicle } from '../services/vehicle';

@Component({
  selector: 'app-reservation-form',
  standalone: true,
  templateUrl: './reservation-form.component.html',
  styleUrls: ['./reservation-form.component.scss'],
  imports: [
  CommonModule,        
  ReactiveFormsModule,
  MatFormFieldModule,
  MatInputModule,
  MatDatepickerModule,
  MatButtonModule,
  MatSelectModule
]

})
export class ReservationFormComponent implements OnInit {
  fb = inject(FormBuilder);
  http = inject(HttpClient);

  today = new Date();
  vehicles: Vehicle[] = [];

  // Declare the form at class level
  reservationForm!: FormGroup;

  ngOnInit(): void {
    // Initialize it inside ngOnInit
    this.reservationForm = this.fb.group({
      location: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      vehicleId: ['', Validators.required]
    });

    this.http.get<Vehicle[]>('/api/vehicles/available').subscribe(data => {
      this.vehicles = data;
    });
  }

  submit(): void {
    const { startDate, endDate } = this.reservationForm.value;

    if (new Date(endDate) <= new Date(startDate)) {
      alert('End date must be after start date');
      return;
    }

    if (this.reservationForm.valid) {
      this.http.post('/api/reservations', this.reservationForm.value).subscribe(() => {
        alert('Reserva realizada con éxito');
        this.reservationForm.reset();
      });
    }
  }
}
