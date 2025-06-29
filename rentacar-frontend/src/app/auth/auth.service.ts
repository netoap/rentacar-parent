import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { decodeToken } from './jwt-utils';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { UserProfile } from './user-profile.model';
import { catchError, throwError } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private authState = new BehaviorSubject<boolean>(this.checkToken());

  auth$ = this.authState.asObservable();
  http = inject(HttpClient);
  router = inject(Router);

  constructor() {
    // Optional: poll every 15s to detect expiration
    setInterval(() => {
      if (!this.checkToken()) {
        this.logout();
      }
    }, 50000);
  }
 
  login(username: string, password: string): Observable<{ token: string }> {
    return this.http
      .post<{ token: string }>(`${environment.apiBaseUrl}/auth/login`, {
        username,
        password,
      })
      .pipe(
        tap((response) => {
          localStorage.setItem('token', response.token);
          this.authState.next(true);
        })
      );
  }

  logout(): void {
    localStorage.removeItem('token');
    this.authState.next(false);
    this.router.navigate(['/login']); // sin recargar la app
  }

  isAuthenticated(): boolean {
    return this.authState.value;
  }

  private checkToken(): boolean {
    const token = localStorage.getItem('token');
    if (!token) return false;

    const payload = decodeToken(token);
    const now = Math.floor(Date.now() / 1000);
    return !!payload?.exp && payload.exp > now;
  }

  getCurrentUser(): { email: string; role: string } | null {
    const token = localStorage.getItem('token');
    const payload = token ? decodeToken(token) : null;

    if (!payload) return null;

    const rawRole = Array.isArray(payload.roles)
      ? payload.roles[0]
      : payload.role || 'USER';

    const role = rawRole.replace('ROLE_', '');

    return {
      email: payload.sub,
      role,
    };
  }

  getRole(): string | null {
    return this.getCurrentUser()?.role ?? null;
  }

  getEmail(): string | null {
    return this.getCurrentUser()?.email ?? null;
  }

  getUserProfile(): Observable<UserProfile> {
  return this.http.get<UserProfile>(`${environment.apiBaseUrl}/auth/me`).pipe(
    tap(profile => {
      console.log('Perfil recibido:', profile)}),
    catchError(err => {
      console.error('Error al obtener el perfil:', err);
      return throwError(() => err);
    })
  );
}


}
