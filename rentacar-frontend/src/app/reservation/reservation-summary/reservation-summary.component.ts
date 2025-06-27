import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-reservation-summary',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule],
  templateUrl: './reservation-summary.component.html',
  styleUrls: ['./reservation-summary.component.scss'],
})
export class ReservationSummaryComponent implements OnInit {
  route = inject(ActivatedRoute);
  http = inject(HttpClient);
  router = inject(Router);
  auth = inject(AuthService);

  vehicle: any;
  vehicleId!: number;
  startDate!: Date;
  endDate!: Date;
  totalPrice = 0;
  loading = true;

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.vehicleId = +params['vehicleId'];
      this.startDate = new Date(params['startDate']);
      this.endDate = new Date(params['endDate']);

      if (!this.vehicleId || !this.startDate || !this.endDate) {
        this.router.navigate(['/vehicles']);
        return;
      }

      this.fetchVehicle();
    });
  }

  private fetchVehicle(): void {
    this.http.get(`/api/v1/vehicles/${this.vehicleId}`).subscribe({
      next: (data: any) => {
        this.vehicle = data;
        this.calculateTotal();
        this.loading = false;
      },
      error: () => {
        this.router.navigate(['/vehicles']);
      },
    });
  }

  private calculateTotal(): void {
    const dayCount =
      (this.endDate.getTime() - this.startDate.getTime()) /
        (1000 * 60 * 60 * 24) +
      1;
    this.totalPrice = dayCount * this.vehicle.price;
  }

  confirm(): void {
    const user = this.auth.getCurrentUser();

    if (!user) {
      this.router.navigate(['/login']);
      return;
    }

    const payload = {
      customerId: user.email, // use email instead of sub
      vehicleId: this.vehicleId,
      startDate: this.startDate,
      endDate: this.endDate,
    };

    this.http.post('/api/v1/reservations', payload).subscribe({
      next: () => {
        this.router.navigate(['/my-reservations']);
      },
      error: () => {
        alert('No se pudo confirmar la reserva. Inténtalo nuevamente.');
      },
    });
  }

  cancel(): void {
    this.router.navigate(['/vehicles']);
  }
}
