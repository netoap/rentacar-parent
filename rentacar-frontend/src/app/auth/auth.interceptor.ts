import {
  HttpInterceptorFn,
  HttpRequest,
  HttpHandlerFn,
  HttpErrorResponse
} from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, retry, throwError } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

export const authInterceptor: HttpInterceptorFn = (
  req: HttpRequest<any>,
  next: HttpHandlerFn
) => {
  const token = localStorage.getItem('token');
  const router = inject(Router);
  const snackBar = inject(MatSnackBar);

  const authReq = token
    ? req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      })
    : req;

  return next(authReq).pipe(
    retry(1), //Retry once for transient errors

    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        // Unauthorized → clear token and redirect to login
        localStorage.removeItem('token');
        snackBar.open('Session expired. Please login again.', 'Close', { duration: 3000 });
        router.navigate(['/login']);
      }
      return throwError(() => error);
    })
  );
};
