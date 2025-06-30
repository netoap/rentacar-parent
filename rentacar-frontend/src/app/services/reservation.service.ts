import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface CreateReservationRequest {
  customerEmail: string;
  vehicleId: number;
  startDate: string;
  endDate: string;
}

export interface ReservationResponse {
  id: number;
  customerEmail: string;
  vehicleId: number;
  startDate: string;
  endDate: string;
}

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private baseUrl = `${environment.apiBaseUrl}/reservations`;
  private http = inject(HttpClient);

  createReservation(request: CreateReservationRequest): Observable<ReservationResponse> {
    return this.http.post<ReservationResponse>(this.baseUrl, request);
  }
}
