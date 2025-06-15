import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Vehicle {
  id: number;
  model: string;
  year: number;
  available: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  private readonly API_URL = '/api/v1/vehicles/available';

  constructor(private http: HttpClient) {}

  getAvailableVehicles(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(this.API_URL);
  }
}
