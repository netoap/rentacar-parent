import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-reservation-form',
  standalone: true,
  templateUrl: './reservation-form.component.html',
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
export class ReservationFormComponent {
  fb = inject(FormBuilder);
  http = inject(HttpClient);

  today = new Date();
  vehicles: any[] = [];

  reservationForm: FormGroup = this.fb.group({
    location: '',
    startDate: '',
    endDate: '',
    vehicleId: ''
  });

  ngOnInit() {
    this.http.get<any[]>('/api/vehicles/available').subscribe(data => {
      this.vehicles = data;
    });
  }

  submit() {
    if (this.reservationForm.valid) {
      this.http.post('/api/reservations', this.reservationForm.value).subscribe(() => {
        alert('Reserva realizada con éxito');
        this.reservationForm.reset();
      });
    }
  }
}
