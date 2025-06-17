import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatChipsModule } from '@angular/material/chips';
import { MatSort } from '@angular/material/sort';
import { CommonModule } from '@angular/common';

import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { ReservationDetailsDialogComponent } from '../reservation/reservation-details-dialog.component';


@Component({
  selector: 'app-reservation-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatChipsModule
  ],
  templateUrl: './reservation-list.component.html',
})
export class ReservationListComponent {
  http = inject(HttpClient);
  dialog = inject(MatDialog);
  dataSource = new MatTableDataSource<any>();
  columns: string[] = ['id', 'location', 'dates', 'vehicle', 'status', 'actions'];
  total = 0;
  pageSize = 10;

  ngOnInit() {
    this.loadPage({
      pageIndex: 0, pageSize: this.pageSize,
      length: 0
    });
  }

  loadPage(event: PageEvent) {
    const params = {
      page: event.pageIndex.toString(),
      size: event.pageSize.toString()
    };

    this.http.get<any>('/api/reservations', { params }).subscribe(response => {
      this.dataSource.data = response.content;
      this.total = response.totalElements;
    });
  }

  viewDetails(reservation: any) {
    this.dialog.open(ReservationDetailsDialogComponent, {
      data: reservation,
      width: '400px'
    });
  }

  cancelReservation(reservation: any) {
    if (!confirm('¿Estás seguro de que deseas cancelar esta reserva?')) return;

    this.http.patch(`/api/reservations/${reservation.id}/cancel`, {}).subscribe(() => {
      reservation.status = 'CANCELLED';
      alert('Reserva cancelada.');
    });
  }
}
