import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { ReservationService } from '../services/reservation.service';
import { AuthService } from '../auth/auth.service';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

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

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      const { start, end, location } = params;

      this.form = this.fb.group({
        vehicleId: [
          this.route.snapshot.paramMap.get('vehicleId'),
          Validators.required,
        ],
        startDate: [start || '', Validators.required],
        endDate: [end || '', Validators.required],
        location: [{ value: location || '', disabled: true }],
        vehicleModel: [{ value: '', disabled: true }],
      });
    });
    const vehicleId = this.route.snapshot.paramMap.get('vehicleId');

    this.http
      .get(`${environment.apiBaseUrl}/vehicles/${vehicleId}/model`)
      .subscribe({
        next: (model: any) => {/* eslint-disable-line @typescript-eslint/no-explicit-any */
          this.form.patchValue({ vehicleModel: model });
        },
      });
  }

  submit(): void {
    if (this.form.invalid) return;

    const request = {
      customerEmail: this.authService.getEmail()!, // o getCurrentUser()?.email
      vehicleId: this.form.value.vehicleId!,
      startDate: this.form.value.startDate!,
      endDate: this.form.value.endDate!,
    };

    this.reservationService.createReservation(request).subscribe({
      next: () =>
        this.snackBar.open('Reserva creada', 'Cerrar', { duration: 3000 }),
      error: () =>
        this.snackBar.open('Error al crear la reserva', 'Cerrar', {
          duration: 3000,
        }),
    });
  }
}
