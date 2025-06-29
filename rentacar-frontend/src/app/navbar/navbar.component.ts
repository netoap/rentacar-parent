
import { Component, inject } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { UserProfile } from '../auth/user-profile.model'; // ajusta el path según tu modelo
import { Observable } from 'rxjs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button'; // si usas botones
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-navbar',
   imports: [
    CommonModule,
    RouterModule,
    MatToolbarModule,
    MatButtonModule
  ],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {  
  isLoggedIn$!: Observable<boolean>;
  role: string | null = null;
  userName= 'usuario';
  authService=inject(AuthService);

  constructor() {
    this.isLoggedIn$ = this.authService.auth$;
    this.authService.getUserProfile().subscribe((profile: UserProfile) => {
      this.userName = profile.username;
      this.role = profile?.roles[0] || null;
    });
  }

  logout() {
    this.authService.logout();
  }
}
