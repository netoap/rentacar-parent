import { Component, inject, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Vehicle } from '../../services/vehicle';
@Component({
  selector: 'app-admin-vehicles',
  standalone: true,
  templateUrl: './admin-vehicles.component.html',
  styleUrls: ['./admin-vehicles.component.scss'],
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
  ],
})
export class AdminVehiclesComponent implements OnInit {
  vehicles: Vehicle[] = [];
  dataSource = new MatTableDataSource<Vehicle>();
  pageSize = 5;
  pageIndex = 0;
  filterValue = '';
  http = inject(HttpClient);
  router = inject(Router);
  snackBar = inject(MatSnackBar);
  displayedColumns: string[] = ['id', 'model', 'year', 'available', 'actions'];

  ngOnInit(): void {
    this.fetchVehicles();
  }
  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    // puedes llamar a fetchVehicles() si paginas manualmente
  }

  fetchVehicles(): void {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    this.http
      .get<Vehicle[]>(`${environment.apiBaseUrl}/vehicles`, { headers })
      .subscribe({
        next: (data) => {
          this.dataSource.data = data;
          this.applyFilter();
        },
        error: () =>
          this.snackBar.open('Error al cargar vehículos', 'Cerrar', {
            duration: 3000,
          }),
      });
  }

  applyFilter(): void {
    this.dataSource.filter = this.filterValue.trim().toLowerCase();
  }

  goToCreate(): void {
    this.router.navigate(['/admin/create-vehicle']);
  }

  deleteVehicle(id: number): void {
    if (!confirm('¿Eliminar este vehículo?')) return;

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    this.http
      .delete(`${environment.apiBaseUrl}/vehicles/${id}`, { headers })
      .subscribe({
        next: () => {
          this.snackBar.open('Vehículo eliminado', 'Cerrar', {
            duration: 3000,
          });
          this.fetchVehicles();
        },
        error: () =>
          this.snackBar.open('Error al eliminar', 'Cerrar', { duration: 3000 }),
      });
  }

  goToEdit(id: number): void {
    this.router.navigate(['/admin/edit-vehicle', id]);
  }
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  toggleAvailability(vehicle: any): void {
    const newStatus = !vehicle.available;
    this.http
      .patch(`/api/v1/vehicles/${vehicle.id}/availability`, null, {
        params: { available: newStatus },
      })
      .subscribe(() => {
        vehicle.available = newStatus;
        this.snackBar.open(
          `Vehículo ${newStatus ? 'activado' : 'desactivado'}`,
          'Cerrar',
          { duration: 3000 }
        );
      });
  }
}
