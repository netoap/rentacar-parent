import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule,
  ],
})
export class RegisterComponent {
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);

  form: FormGroup;

  constructor() {
    this.form = this.fb.group(
      {
        email: ['', [Validators.required, Validators.email]],
        username: ['', Validators.required],
        password: ['', Validators.required],
        confirmPassword: ['', Validators.required],
      },
      {
        validators: this.passwordsMatchValidator,
      }
    );

    // Sugerir username automáticamente desde el email
    this.form.get('email')?.valueChanges.subscribe((email) => {
      const usernameControl = this.form.get('username');
      if (usernameControl && !usernameControl.dirty) {
        const suggested = email?.split('@')[0] || '';
        usernameControl.setValue(suggested, { emitEvent: false });
      }
    });
  }

  passwordsMatchValidator(group: FormGroup) {
    const pass = group.get('password')?.value;
    const confirm = group.get('confirmPassword')?.value;
    return pass === confirm ? null : { mismatch: true };
  }

  submit(): void {
    if (this.form.invalid) return;

    const { email, username, password } = this.form.value;
    const roles = ['ROLE_USER'];

    this.http
      .post<{ token: string }>('http://localhost:8080/api/auth/register', {
        email,
        username,
        password,
        roles,
      })
      .subscribe({
        next: (response) => {
          localStorage.setItem('token', response.token);
          this.snackBar.open('Registro exitoso. Bienvenido/a.', 'Cerrar', {
            duration: 3000,
          });
          this.router.navigate(['/dashboard']);
        },
        error: (err) => {
          if (err.status === 409 && err.error?.message) {
            if (err.error.message.includes('username')) {
              this.form.get('username')?.setErrors({ taken: true });
            } else if (err.error.message.includes('email')) {
              this.form.get('email')?.setErrors({ taken: true });
            }
          }

          const msg =
            err.status === 409
              ? 'Ya existe una cuenta con esos datos.'
              : 'Error al registrar. Inténtalo de nuevo.';

          this.snackBar.open(msg, 'Cerrar', {
            duration: 3000,
          });
        },
      });
  }
}
