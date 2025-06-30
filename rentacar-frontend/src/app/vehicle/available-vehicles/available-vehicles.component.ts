import { Component, OnInit, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';

import { environment } from '../../../environments/environment';
import { Vehicle } from '../../share/models';

@Component({
  selector: 'app-available-vehicles',
  standalone: true,
  templateUrl: './available-vehicles.component.html',
  styleUrls: ['./available-vehicles.component.scss'],
  imports: [CommonModule, RouterModule, MatCardModule, MatButtonModule],
})
export class AvailableVehiclesComponent implements OnInit {
  private http = inject(HttpClient);

  vehicles: Vehicle[] = [];
  loading = true;

  ngOnInit(): void {
    this.http.get<Vehicle[]>(`${environment.apiBaseUrl}/vehicles/available`)
      .subscribe({
        next: (data) => {
          this.vehicles = data;
          this.loading = false;
        },
        error: () => {
          this.vehicles = [];
          this.loading = false;
        }
      });
  }
}
