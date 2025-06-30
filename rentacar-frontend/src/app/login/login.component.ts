import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { Router } from '@angular/router';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { LoginResponse } from './login-response';
import { AuthService } from '../auth/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
  ],
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);

  loginForm: FormGroup;

  constructor() {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  onSubmit(): void {
  if (this.loginForm.invalid) return;

  const { username, password } = this.loginForm.value;

  this.authService.login(username, password).subscribe({
    next: (res: LoginResponse) => {
      localStorage.setItem('token', res.token);
      this.snackBar.open('Inicio de sesión exitoso', 'Cerrar', { duration: 3000 });

      const user = this.authService.getCurrentUser();
      const role = user?.role ?? 'CUSTOMER';

      if (role === 'ADMIN') {
        this.router.navigate(['/admin']);
      } else {
        this.router.navigate(['/vehicles']);
      }
    },
    error: () => {
      this.snackBar.open('Usuario o contraseña incorrectos', 'Cerrar', {
        duration: 3000,
      });
    },
  });
}

}
