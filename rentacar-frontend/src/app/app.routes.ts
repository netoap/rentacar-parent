import { Routes } from '@angular/router';
import { authGuard } from './auth/auth-guard';
import { GuestGuard } from './auth/guest-guard';
import { roleGuard } from './auth/role.guard';

export const routes: Routes = [
  // Public
  {
    path: '',
    loadComponent: () => import('./landing/landing.component').then(m => m.LandingComponent)
  },
  {
    path: 'login',
    loadComponent: () => import('./login/login.component').then(m => m.LoginComponent),
    canActivate: [GuestGuard]
  },
  {
    path: 'register',
    loadComponent: () => import('./register/register.component').then(m => m.RegisterComponent),
    canActivate: [GuestGuard]
  },

  // Protected routes
  {
    path: 'dashboard',
    loadComponent: () => import('./dashboard/dashboard.component').then(m => m.DashboardComponent),
    canActivate: [authGuard]
  },
  {
    path: 'admin',
    loadComponent: () => import('./dashboard/dashboard.component').then(m => m.DashboardComponent),
    canActivate: [authGuard]
  },
  {
    path: 'vehicles',
    loadComponent: () => import('./vehicle-list/vehicle-list.component').then(m => m.VehicleListComponent),
    canActivate: [authGuard]
  },
  {
    path: 'reserve/:vehicleId',
    loadComponent: () => import('./reservation-form/reservation-form.component').then(m => m.ReservationFormComponent),
    canActivate: [authGuard]
  },
  {
    path: 'my-reservations',
    loadComponent: () => import('./reservation-list/reservation-list.component').then(m => m.ReservationListComponent),
    canActivate: [authGuard]
  },
  {
    path: 'reservation-summary',
    loadComponent: () => import('./reservation/reservation-summary/reservation-summary.component').then(m => m.ReservationSummaryComponent),
    canActivate: [authGuard]
  },
  {
    path: 'vehicles/:vehicleId/calendar',
    loadComponent: () => import('./vehicle/vehicle-calendar/vehicle-calendar.component').then(m => m.VehicleCalendarComponent),
    canActivate: [authGuard]
  },
  {
  path: 'admin',
  loadComponent: () => import('./dashboard/dashboard.component').then(m => m.DashboardComponent),
  canActivate: [authGuard, roleGuard],
  data: { expectedRole: 'ADMIN' }
},

  // Fallback
  { path: '**', redirectTo: '' }
];
