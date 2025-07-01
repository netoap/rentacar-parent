import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { environment } from '../../environments/environment';
import { ReservationService } from '../services/reservation.service';
import { AuthService } from '../auth/auth.service';
import { dateRangeValidator } from '../share/dateRangeValidator';
import { Router } from '@angular/router';


@Component({
  selector: 'app-reservation-form',
  standalone: true,
  templateUrl: './reservation-form.component.html',
  styleUrls: ['./reservation-form.component.scss'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSnackBarModule,
  ],
})
export class ReservationFormComponent implements OnInit {
  form!: FormGroup;

  private fb = inject(FormBuilder);
  private reservationService = inject(ReservationService);
  private authService = inject(AuthService);
  private snackBar = inject(MatSnackBar);
  private route = inject(ActivatedRoute);
  private http = inject(HttpClient);
  private router = inject(Router); 

  ngOnInit(): void {
    const vehicleId = this.route.snapshot.paramMap.get('vehicleId');

    this.form = this.fb.group(
      {
        vehicleId: [vehicleId, Validators.required],
        startDate: ['', Validators.required],
        endDate: ['', Validators.required],
        location: [{ value: 'Aeropuerto de Madrid', disabled: true }],
        vehicleModel: [{ value: '', disabled: true }],
        email: [{ value: this.authService.getEmail(), disabled: true }],
      },
      {
        validators: [dateRangeValidator], // 👈 aquí se aplica
      }
    );

    this.route.queryParams.subscribe((params) => {
      this.form.patchValue({
        startDate: params['start'] || '',
        endDate: params['end'] || '',
      });
    });

    this.http
      .get<{ model: string }>(
        `${environment.apiBaseUrl}/vehicles/${vehicleId}/model`
      )
      .subscribe({
        next: (res) => {
          this.form.patchValue({ vehicleModel: res.model });
        },
        error: () => {
          this.snackBar.open(
            'No se pudo obtener el modelo del vehículo',
            'Cerrar',
            {
              duration: 3000,
            }
          );
        },
      });
  }

  formatDate(date: Date): string {
    return new Date(date).toISOString().split('T')[0]; // "YYYY-MM-DD"
  }

  submit(): void {
    if (this.form.invalid) return;

    const { startDate, endDate, vehicleId } = this.form.value;

    const request = {
      customerEmail: this.authService.getEmail()?? '',
      vehicleId: +vehicleId,
      startDate: this.formatDate(startDate),
      endDate: this.formatDate(endDate),
    };
    console.log('Request to create reservation:', request);

    this.reservationService.createReservation(request).subscribe({
      next: () =>{
        this.snackBar.open('Reserva creada', 'Cerrar', { duration: 3000 });
        this.router.navigate(['/dashboard/my-reservations']); // 👈 redirección automática
      },
      error: (err) => {
        const message =
          err.status === 409
            ? 'Este vehículo ya está reservado en esas fechas.'
            : 'Error al crear la reserva';
        this.snackBar.open(message, 'Cerrar', { duration: 3000 });
      },
    });
  }
}
