import { Routes } from '@angular/router';
import { authGuard } from './auth/auth-guard';
import { GuestGuard } from './auth/guest-guard';
import { roleGuard } from './auth/role.guard';
import { AdminLayoutComponent } from './admin/layout/admin-layout/admin-layout.component';
import { UserLayoutComponent } from './dashboard/layout/user-layout/user-layout.component';
import { ROLES } from './auth/roles';
import { ROUTES_PATH } from './routing/app.routes.constants';

export const routes: Routes = [
  // 🌐 Public
  {
    path: ROUTES_PATH.ROOT,
    loadComponent: () =>
      import('./landing/landing.component').then(m => m.LandingComponent),
  },
  {
    path: ROUTES_PATH.LOGIN,
    loadComponent: () =>
      import('./login/login.component').then(m => m.LoginComponent),
    canActivate: [GuestGuard],
  },
  {
    path: ROUTES_PATH.REGISTER,
    loadComponent: () =>
      import('./register/register.component').then(m => m.RegisterComponent),
    canActivate: [GuestGuard],
  },

  // 👤 User area
  {
  path: 'vehicles',
  loadComponent: () =>
    import('./vehicle-list/vehicle-list.component').then(m => m.VehicleListComponent)
}
,
  {
    path: ROUTES_PATH.DASHBOARD,
    component: UserLayoutComponent,
    canActivate: [authGuard],
    children: [
      {
        path: ROUTES_PATH.ROOT,
        loadComponent: () =>
          import('./dashboard/dashboard.component').then(m => m.DashboardComponent),
      },
      {
        path: ROUTES_PATH.DASHBOARD_VEHICLES,
        loadComponent: () =>
          import('./vehicle-list/vehicle-list.component').then(m => m.VehicleListComponent),
      },
      {
        path: ROUTES_PATH.RESERVE,
        loadComponent: () =>
          import('./reservation-form/reservation-form.component').then(m => m.ReservationFormComponent),
      },
      {
        path: ROUTES_PATH.MY_RESERVATIONS,
        loadComponent: () =>
          import('./reservation/my-reservations/my-reservations.component').then(m => m.MyReservationsComponent),
      },
      {
        path: ROUTES_PATH.RESERVATION_SUMMARY,
        loadComponent: () =>
          import('./reservation/reservation-summary/reservation-summary.component').then(m => m.ReservationSummaryComponent),
      },
      {
        path: ROUTES_PATH.VEHICLE_CALENDAR,
        loadComponent: () =>
          import('./vehicle/vehicle-calendar/vehicle-calendar.component').then(m => m.VehicleCalendarComponent),
      }
    ]
  },

  // 🛠️ Admin area
  {
    path: ROUTES_PATH.ADMIN,
    component: AdminLayoutComponent,
    canActivate: [authGuard, roleGuard],
    data: { expectedRole: ROLES.ADMIN },
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      },
      {
        path: ROUTES_PATH.ADMIN_DASHBOARD,
        loadComponent: () =>
          import('./admin/dashboard/admin-dashboard.component').then(m => m.AdminDashboardComponent),
        
      },
      {
        path: ROUTES_PATH.ADMIN_VEHICLES,
        loadComponent: () =>
          import('./admin/admin-vehicles/admin-vehicles.component').then(m => m.AdminVehiclesComponent),
      },
      {
        path: ROUTES_PATH.ADMIN_CREATE_VEHICLE,
        loadComponent: () =>
          import('./admin/create-vehicle/create-vehicle.component').then(m => m.CreateVehicleComponent),
      },
      {
        path: ROUTES_PATH.ADMIN_EDIT_VEHICLE,
        loadComponent: () =>
          import('./admin/edit-vehicle/edit-vehicle.component').then(m => m.EditVehicleComponent),
      },
      {
        path: ROUTES_PATH.ADMIN_RESERVATIONS,
        loadComponent: () =>
          import('./reservation/admin-reservations/admin-reservations.component').then(m => m.AdminReservationsComponent),
      }
    ]
  },

  // ❌ Errors
  {
    path: ROUTES_PATH.UNAUTHORIZED,
    loadComponent: () =>
      import('./errors/unauthorized/unauthorized.component').then(m => m.UnauthorizedComponent),
  },
  {
    path: '**',
    loadComponent: () =>
      import('./errors/not-found/not-found.component').then(m => m.NotFoundComponent),
  }
];
