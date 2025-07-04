import { Routes } from '@angular/router';
import { authGuard } from './auth/auth-guard';
import { GuestGuard } from './auth/guest-guard';
import { roleGuard } from './auth/role.guard';
import { redirectGuard } from './auth/redirect.guard';
import { AdminLayoutComponent } from './admin/layout/admin-layout/admin-layout.component';
import { UserLayoutComponent } from './dashboard/layout/user-layout/user-layout.component';
import { ROLES } from './auth/roles';

export const routes: Routes = [
  // 🏠 Redirección raíz
  {
    path: '',
    canActivate: [redirectGuard],
    loadComponent: () =>
      import('./redirect-page').then((m) => m.RedirectPageComponent),
  },

  // 🌐 Públicas
  {
    path: 'home',
    loadComponent: () =>
      import('./landing/landing.component').then((m) => m.LandingComponent),
  },
  {
    path: 'login',
    canActivate: [GuestGuard],
    loadComponent: () =>
      import('./login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: 'register',
    canActivate: [GuestGuard],
    loadComponent: () =>
      import('./register/register.component').then((m) => m.RegisterComponent),
  },
  {
    path: 'vehicles', // pública
    loadComponent: () =>
      import('./vehicle/available-vehicles/available-vehicles.component').then(
        (m) => m.AvailableVehiclesComponent
      ),
  },

  // 👤 Área usuario (dashboard)
  {
    path: 'dashboard',
    component: UserLayoutComponent,
    canActivate: [authGuard],
    children: [
      {
        path: '',
        loadComponent: () =>
          import('./dashboard/dashboard.component').then((m) => m.DashboardComponent),
      },
      {
        path: 'reserve/:vehicleId',
        loadComponent: () =>
          import('./reservation-form/reservation-form.component').then((m) => m.ReservationFormComponent),
      },
      {
        path: 'payment',
        loadComponent: () =>
          import('./payment/payment.component').then((m) => m.PaymentComponent),
      },
      {
        path: 'my-reservations',
        loadComponent: () =>
          import('./reservation/my-reservations/my-reservations.component').then(
            (m) => m.MyReservationsComponent
          ),
      },
      {
        path: 'reservation-summary',
        loadComponent: () =>
          import('./reservation/reservation-summary/reservation-summary.component').then(
            (m) => m.ReservationSummaryComponent
          ),
      },
      {
        path: 'vehicles/:vehicleId/calendar',
        loadComponent: () =>
          import('./vehicle/vehicle-calendar/vehicle-calendar.component').then(
            (m) => m.VehicleCalendarComponent
          ),
      },
    ],
  },

  // 🛠️ Área admin
  {
    path: 'admin',
    component: AdminLayoutComponent,
    canActivate: [authGuard, roleGuard],
    data: { expectedRole: ROLES.ADMIN },
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
      },
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./admin/dashboard/admin-dashboard.component').then(
            (m) => m.AdminDashboardComponent
          ),
      },
      {
        path: 'vehicles',
        loadComponent: () =>
          import('./admin/admin-vehicles/admin-vehicles.component').then(
            (m) => m.AdminVehiclesComponent
          ),
      },
      {
        path: 'create-vehicle',
        loadComponent: () =>
          import('./admin/create-vehicle/create-vehicle.component').then(
            (m) => m.CreateVehicleComponent
          ),
      },
      {
        path: 'edit-vehicle/:id',
        loadComponent: () =>
          import('./admin/edit-vehicle/edit-vehicle.component').then(
            (m) => m.EditVehicleComponent
          ),
      },
      {
        path: 'admin-reservations',
        loadComponent: () =>
          import('./reservation/admin-reservations/admin-reservations.component').then(
            (m) => m.AdminReservationsComponent
          ),
      },
    ],
  },

  // ❌ Páginas de error
  {
    path: 'unauthorized',
    loadComponent: () =>
      import('./errors/unauthorized/unauthorized.component').then(
        (m) => m.UnauthorizedComponent
      ),
  },
  {
    path: '**',
    loadComponent: () =>
      import('./errors/not-found/not-found.component').then(
        (m) => m.NotFoundComponent
      ),
  },
];
