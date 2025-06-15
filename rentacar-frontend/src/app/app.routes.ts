import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { authGuard } from './auth/auth-guard';
import { GuestGuard } from './auth/guest-guard';

import { VehicleListComponent } from './vehicle-list/vehicle-list.component';

  import { ReservationFormComponent } from './reservation-form/reservation-form.component';



export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard]},
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent, canActivate: [GuestGuard] },
  { path: 'vehicles', component: VehicleListComponent, canActivate: [authGuard] },
  { path: 'reserve/:vehicleId', component: ReservationFormComponent, canActivate: [authGuard] },
  { path: 'admin', component: DashboardComponent, canActivate: [authGuard]},
];
