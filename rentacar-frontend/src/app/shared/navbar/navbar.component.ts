import { Component, inject } from '@angular/core';
import { AuthService } from '../../auth/auth.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-navbar',
  standalone: true,
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  imports: [
    CommonModule,
    RouterModule,
    MatToolbarModule,
    MatButtonModule
  ]
})
export class NavbarComponent {
  private auth = inject(AuthService);

  isLoggedIn$ = this.auth.auth$; // ✅ needed for async pipe to work
  role = this.auth.getRole();
  email = this.auth.getEmail();

  logout(): void {
    this.auth.logout();
  }
}
