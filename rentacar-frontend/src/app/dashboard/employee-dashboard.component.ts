import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';

@Component({
  selector: 'app-employee-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatListModule],
  template: `
    <h2>Panel de Empleado</h2>

    <mat-card>
      <h3>Entregas programadas hoy</h3>
      <mat-list>
        <mat-list-item *ngFor="let pickup of pickupsToday">
          {{ pickup.time }} – {{ pickup.vehicle }} ({{ pickup.customer }})
        </mat-list-item>
      </mat-list>
    </mat-card>

    <mat-card>
      <h3>Vehículos para preparación</h3>
      <mat-list>
        <mat-list-item *ngFor="let car of vehiclesToPrep">
          {{ car }}
        </mat-list-item>
      </mat-list>
    </mat-card>
  `,
  styles: [`
    mat-card {
      margin-bottom: 1rem;
    }
  `]
})
export class EmployeeDashboardComponent {
  pickupsToday = [
    { time: '10:00', vehicle: 'Toyota Yaris', customer: 'Ana Pérez' },
    { time: '15:30', vehicle: 'Ford Focus', customer: 'Luis Gómez' }
  ];

  vehiclesToPrep = [
    'Nissan Leaf - Mantenimiento',
    'Renault Clio - Limpieza post-reserva',
  ];
}
