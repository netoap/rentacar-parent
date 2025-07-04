import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { MatCardModule } from '@angular/material/card';
import { AuthService } from '../../auth/auth.service';
import { Reservation } from '../../share/models';
import { FormsModule } from '@angular/forms';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatIconModule } from '@angular/material/icon';
import { environment } from '../../../environments/environment';
import { RouterModule } from '@angular/router';


@Component({
  selector: 'app-my-reservations',
  standalone: true,
  imports: [CommonModule, RouterModule, MatCardModule, FormsModule, MatCheckboxModule,MatIconModule],
  templateUrl: './my-reservations.component.html',
  styleUrls: ['./my-reservations.component.scss'],
})
export class MyReservationsComponent implements OnInit {
  private http = inject(HttpClient);
  private auth = inject(AuthService);

  reservations: Reservation[] = [];

  ngOnInit(): void {
    const user = this.auth.getCurrentUser();
    if (!user) return;

    console.log('Usuario:', this.auth.getCurrentUser());

this.http.get<Reservation[]>(`${environment.apiBaseUrl}/reservations/mine`)
  .subscribe({
    next: (data) => {
      console.log('Reservas recibidas:', data);
      this.reservations = data;
    },
    error: (err) => {
      console.error('Error al cargar reservas:', err);
    }
  });
  }
  cancel(reservationId: number): void {
    if (!confirm('¿Estás seguro de cancelar esta reserva?')) return;

    this.http
      .patch(`/api/v1/reservations/${reservationId}/cancel`, {})
      .subscribe({
        next: () => {
          this.reservations = this.reservations.map((r) =>
            r.id === reservationId ? { ...r, status: 'CANCELLED' } : r
          );
        },
        error: () => {
          alert('Error al cancelar la reserva.');
        },
      });
  }
  showOnlyActive = false;

  get filteredReservations(): Reservation[] {
    return this.showOnlyActive
      ? this.reservations.filter((r) => r.status !== 'CANCELLED')
      : this.reservations;
  }
}
