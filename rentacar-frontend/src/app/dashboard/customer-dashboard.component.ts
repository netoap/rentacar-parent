import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-customer-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule, RouterModule],
  template: `
    <h2>Bienvenido, cliente</h2>

    <div class="dashboard-cards">
      <mat-card>
        <h3>Mis reservas activas</h3>
        <p>{{ activeReservations }} reservas activas</p>
      </mat-card>

      <mat-card>
        <h3>Próxima devolución</h3>
        <p>{{ nextReturn | date }}</p>
      </mat-card>

      <mat-card class="action-card" routerLink="/reservar">
        <h3>Haz una nueva reserva</h3>
        <p>Encuentra el coche ideal para tu próximo viaje</p>
      </mat-card>
    </div>
  `,
  styles: [`
    .dashboard-cards {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
      gap: 1rem;
      margin-top: 1rem;
    }
    .action-card {
      cursor: pointer;
      transition: 0.3s ease;
    }
    .action-card:hover {
      background-color: #f0f0f0;
    }
  `]
})
export class CustomerDashboardComponent {
  activeReservations = 2;
  nextReturn = new Date(new Date().setDate(new Date().getDate() + 3)); // 3 days from now
}
