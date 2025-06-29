import { Component, inject, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-dashboard',
  imports: [MatCardModule, MatIconModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  user: { email: string; role: string } | null = null;
  authService = inject(AuthService);

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser();
  }
  get displayName(): string {
    return this.user?.email ? `, ${this.user.email}` : '';
  }
}
