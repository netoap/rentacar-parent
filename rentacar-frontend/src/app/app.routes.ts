import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { authGuard } from './auth/auth-guard';
import { GuestGuard } from './auth/guest-guard';

import { VehicleListComponent } from './vehicle-list/vehicle-list.component';
import { ReservationFormComponent } from './reservation-form/reservation-form.component';
import { ReservationListComponent } from './reservation-list/reservation-list.component';
import { LandingComponent } from './landing/landing.component';
import { RegisterComponent } from './register/register.component';







export const routes: Routes = [
  // Default: Show landing page
  { path: '', component: LandingComponent },

  // Login page (only if not authenticated)
  { path: 'login', component: LoginComponent, canActivate: [GuestGuard] },
  { path: 'register', redirectTo: 'login', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent, canActivate: [GuestGuard] },

  // Authenticated routes
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'admin', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'vehicles', component: VehicleListComponent, canActivate: [authGuard] },
  { path: 'reserve/:vehicleId', component: ReservationFormComponent, canActivate: [authGuard] },
  { path: 'my-reservations', component: ReservationListComponent, canActivate: [authGuard] },

  // Fallback route
  { path: '**', redirectTo: '' },
  {
    path: 'dashboard',
    loadComponent: () => import('./dashboard/dashboard-view.component').then(m => m.DashboardViewComponent)
  }

];

