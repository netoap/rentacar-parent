import { Component } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  imports: [CommonModule, MatCardModule, MatButtonModule, RouterModule]
})
export class DashboardComponent {
  email: string | null;
  role: string | null;

  constructor(private auth: AuthService) {
    this.email = this.auth.getEmail();
    this.role = this.auth.getRole();
  }
}
