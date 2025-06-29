import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { MatButtonModule } from '@angular/material/button';
import { Reservation } from '../../share/models';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableDataSource } from '@angular/material/table';
import { ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-admin-reservations',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
  ],
  templateUrl: './admin-reservations.component.html',
  styleUrls: ['./admin-reservations.component.scss'],
})
export class AdminReservationsComponent implements OnInit {
  private http = inject(HttpClient);
  statusFilter = new FormControl('');
  startDateFilter = new FormControl();
  endDateFilter = new FormControl();

  dataSource = new MatTableDataSource<Reservation>([]);
  displayedColumns: string[] = [
    'id',
    'customerId',
    'vehicle',
    'dates',
    'status',
    'actions',
  ];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngOnInit(): void {
    this.http.get<Reservation[]>('/api/v1/reservations').subscribe((data) => {
      this.dataSource.data = data;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  cancel(reservationId: number): void {
    if (!confirm('¿Cancelar esta reserva?')) return;

    this.http
      .patch(`/api/v1/reservations/${reservationId}/cancel`, {})
      .subscribe(() => {
        this.dataSource.data = this.dataSource.data.map((r) =>
          r.id === reservationId ? { ...r, status: 'CANCELLED' } : r
        );
      });
  }

  get filteredData(): Reservation[] {
    return this.dataSource.data.filter((res) => {
      const statusMatch =
        !this.statusFilter.value || res.status === this.statusFilter.value;
      const startMatch =
        !this.startDateFilter.value ||
        new Date(res.startDate) >= this.startDateFilter.value;
      const endMatch =
        !this.endDateFilter.value ||
        new Date(res.endDate) <= this.endDateFilter.value;
      return statusMatch && startMatch && endMatch;
    });
  }
}
