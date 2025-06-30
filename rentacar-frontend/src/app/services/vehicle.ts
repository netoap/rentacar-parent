import { Injectable, inject } from '@angular/core';
import { HttpClient,HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Vehicle as VehicleModel } from '../share/models';

import { Vehicle } from '../share/models'; // Import the Vehicle interface

@Injectable({
  providedIn: 'root'
})
export class VehicleService {
  private http = inject(HttpClient);
  private readonly API = `${environment.apiBaseUrl}/vehicles`;


  private readonly API_URL = '/api/v1/vehicles/available';

  getAvailableVehicles(): Observable<VehicleModel[]> {
    return this.http.get<VehicleModel[]>(this.API_URL);
  }

   getAvailableVehiclesByDateRange(startDate: string, endDate: string): Observable<Vehicle[]> {
    const params = new HttpParams()
      .set('start', startDate)
      .set('end', endDate);

    return this.http.get<VehicleModel[]>(`${this.API}/available-range`, { params });
  }
}
