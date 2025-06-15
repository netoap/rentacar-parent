import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginResponse } from '../login/login-response';
import { decodeToken, JwtPayload } from './jwt-utils';
import { of } from 'rxjs';
import { delay } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_URL = '/api/auth/login';
  
  constructor(private http: HttpClient) { }

  /*login(email: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.API_URL, {email, password});
  }*/


login(email: string, password: string): Observable<{ token: string }> {
  // Simulate a successful login response with a fake JWT
  const fakeToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwicm9sZSI6IkNVU1RPTUVSIiwiZXhwIjoxODAwMDAwMDAwfQ.dummy';
  return of({ token: fakeToken }).pipe(delay(500)); // simulate network delay
}


  logout(): void {
    localStorage.removeItem('token');
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    if (!token) return false;

    const payload = decodeToken(token);
    if (!payload || !payload.exp) return false;

    const now = Math.floor(Date.now() / 1000); // in seconds
    const isExpired = payload.exp < now;

    if (isExpired) {
      this.logout(); // auto-logout
      return false;
    }

    return true;
  }

  getUser(): JwtPayload | null {
    const token = localStorage.getItem('token');
    return token ? decodeToken(token) : null;
  }

  getRole(): string | null {
    return this.getUser()?.role ?? null;
  }

  getEmail(): string | null {
    return this.getUser()?.sub ?? null;
  }
}
