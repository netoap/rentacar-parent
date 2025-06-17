import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-reservation-details-dialog',
  standalone: true,
  imports: [CommonModule, MatDialogModule],
  template: `
    <h2 mat-dialog-title>Detalles de la reserva</h2>
    <mat-dialog-content>
      <p><strong>ID:</strong> {{ data.id }}</p>
      <p><strong>Ubicación:</strong> {{ data.location }}</p>
      <p><strong>Fechas:</strong> {{ data.startDate | date }} - {{ data.endDate | date }}</p>
      <p><strong>Vehículo:</strong> {{ data.vehicle.model }} ({{ data.vehicle.year }})</p>
      <p><strong>Estado:</strong> {{ data.status }}</p>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cerrar</button>
    </mat-dialog-actions>
  `
})
export class ReservationDetailsDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}
}