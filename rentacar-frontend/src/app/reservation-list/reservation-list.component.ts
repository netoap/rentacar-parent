import { Component, inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-reservation-list',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatTableModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatPaginatorModule
  ],
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.scss']
})
export class ReservationListComponent implements OnInit {
  private http = inject(HttpClient);

  reservations: Reservation[] = [];
  filteredReservations: Reservation[] = [];

  statusFilter = new FormControl('');
  searchTerm = new FormControl('');

  pageSize = 5;
  pageIndex = 0;

  ngOnInit(): void {
    this.loadReservations();

    this.statusFilter.valueChanges.subscribe(() => this.applyFilters());
    this.searchTerm.valueChanges.subscribe(() => this.applyFilters());
  }

  loadReservations(): void {
    this.http.get<Reservation[]>('/api/reservations/my').subscribe(data => {
      this.reservations = data;
      this.applyFilters();
    });
  }

  applyFilters(): void {
    const status = this.statusFilter.value;
    const search = this.searchTerm.value?.toLowerCase() ?? '';

    this.filteredReservations = this.reservations.filter(r =>
      (!status || r.status === status) &&
      (r.vehicleModel.toLowerCase().includes(search) ||
       r.id.includes(search))
    );
  }

  onPage(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
  }

  get paginatedReservations(): Reservation[] {
    const start = this.pageIndex * this.pageSize;
    return this.filteredReservations.slice(start, start + this.pageSize);
  }
}
interface Reservation {
  id: string;
  vehicleModel: string;
  startDate: string;
  endDate: string;
  status: 'UPCOMING' | 'COMPLETED' | 'CANCELLED';
}