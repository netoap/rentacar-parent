import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { MatCardModule } from '@angular/material/card';


@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule],
  template: `
    <h2>Panel de Administración</h2>
    <div class="dashboard-cards">
      <mat-card>Total de usuarios: {{ totalUsers }}</mat-card>
      <mat-card>Reservas esta semana: {{ weeklyReservations }}</mat-card>
      <mat-card>Ingresos este mes: €{{ revenue }}</mat-card>
    </div>
  `,
  styles: [`
    .dashboard-cards {
      display: flex;
      gap: 1rem;
      margin-top: 1rem;
    }
  `]
})
export class AdminDashboardComponent {
  totalUsers = 42;
  weeklyReservations = 15;
  revenue = 1280;
}
