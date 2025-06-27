import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';

export const roleGuard: CanActivateFn = (route, state) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  const user = auth.getCurrentUser();
  const expectedRole = route.data?.['expectedRole'];

  if (user && user.role === expectedRole) {
    return true;
  }

  router.navigate(['/dashboard']);
  return false;
};
