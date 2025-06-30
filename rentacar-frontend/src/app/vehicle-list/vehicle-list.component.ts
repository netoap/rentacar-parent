/* eslint-disable @typescript-eslint/no-explicit-any */
import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-vehicle-list',
  standalone: true,
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.scss'],
  imports: [CommonModule, RouterModule, MatCardModule, MatButtonModule],
})
export class VehicleListComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private http = inject(HttpClient);

  loading = true;
  vehiclesByCategory = new Map<string, any[]>();
  lastSearchParams: { location?: string; start: string; end: string } | null =
    null;

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      const { location, start, end } = params;

      if (!location || !start || !end) {
        this.loading = false;
        console.warn('Missing required search parameters');
        return;
      }

      this.lastSearchParams = { location, start, end };
      this.fetchAvailableVehicles(location, start, end);
    });
  }

  retry(): void {
    if (this.lastSearchParams) {
      this.loading = true;
      this.vehiclesByCategory.clear();
      this.fetchAvailableVehicles(
        this.lastSearchParams.location!,
        this.lastSearchParams.start,
        this.lastSearchParams.end
      );
    }
  }

  private fetchAvailableVehicles( location: string, start: string, end: string ): void {
    const url = `${environment.apiBaseUrl}/vehicles/available?from=${start}&to=${end}&location=${encodeURIComponent(location)}`;

    this.http.get<any[]>(url).subscribe({
      next: (data) => {
        this.vehiclesByCategory = this.groupByCategory(data);
        this.loading = false;
      },
      error: () => {
        this.vehiclesByCategory.clear();
        this.loading = false;
      },
    });
  }

  private groupByCategory(vehicles: any[]): Map<string, any[]> {
    const grouped = new Map<string, any[]>();
    for (const car of vehicles) {
      const category = car.category || 'Otros';
      if (!grouped.has(category)) {
        grouped.set(category, []);
      }
      grouped.get(category)!.push(car);
    }
    return grouped;
  }
}
