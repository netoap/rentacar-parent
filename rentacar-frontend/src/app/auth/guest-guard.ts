import { inject, Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class GuestGuard implements CanActivate {

  auth=inject(AuthService);
  router = inject(Router);

  canActivate(): boolean {
    if (this.auth.isAuthenticated()) {
      // Redirect authenticated users away from login/register pages
      this.router.navigate(['/dashboard']);
      return false;
    }
    return true;
  }
}
