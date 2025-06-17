import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth/auth.service'; // your JWT service
import { AdminDashboardComponent } from './admin-dashboard.component';
import { CustomerDashboardComponent } from './customer-dashboard.component';
import { EmployeeDashboardComponent } from './employee-dashboard.component';


@Component({
  selector: 'app-dashboard-view',
  standalone: true,
  imports: [CommonModule, AdminDashboardComponent, CustomerDashboardComponent, EmployeeDashboardComponent],
  template: `
    <ng-container [ngSwitch]="role">
      <app-admin-dashboard *ngSwitchCase="'ADMIN'"></app-admin-dashboard>
      <app-customer-dashboard *ngSwitchCase="'CUSTOMER'"></app-customer-dashboard>
      <app-employee-dashboard *ngSwitchCase="'EMPLOYEE'"></app-employee-dashboard>
      <p *ngSwitchDefault>No tiene un rol válido.</p>
    </ng-container>
  `
})
export class DashboardViewComponent {
  auth = inject(AuthService);
  // e.g., 'ADMIN' | 'CUSTOMER' | 'EMPLOYEE'
  role = this.auth.getCurrentUserRole();
}
