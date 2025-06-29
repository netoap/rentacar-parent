
import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';

export const roleGuard: CanActivateFn = (route) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const user = authService.getCurrentUser();

  const expected = route.data['expectedRole'];
  const expectedRoles = Array.isArray(expected) ? expected : [expected];

  if (user && expectedRoles.includes(user.role)) {
    return true;
  }

  return router.createUrlTree(['/unauthorized']);
};
