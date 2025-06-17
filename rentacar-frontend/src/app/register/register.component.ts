import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
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
    MatSnackBarModule
  ]
})
export class RegisterComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    }, {
      validators: this.passwordsMatchValidator
    });
  }

  passwordsMatchValidator(group: FormGroup) {
    const pass = group.get('password')?.value;
    const confirm = group.get('confirmPassword')?.value;
    return pass === confirm ? null : { mismatch: true };
  }

  submit(): void {
  if (this.form.invalid) return;

  const { email, password } = this.form.value;

  this.http.post<{ token: string }>('/api/auth/register', { email, password }).subscribe({
    next: (response) => {
      // Store the returned token
      localStorage.setItem('token', response.token);
      this.snackBar.open(' Registro exitoso. Bienvenido/a.', 'Cerrar', { duration: 3000 });
      this.router.navigate(['/dashboard']);
    },
    error: (err) => {
      if (err.status === 409) {
        // Example: server returns 409 Conflict when email already exists
        this.snackBar.open(' El email ya está en uso. Intenta con otro.', 'Cerrar', {
          duration: 3000
        });
      } else {
        this.snackBar.open(' Error al registrar. Inténtalo de nuevo.', 'Cerrar', {
          duration: 3000
        });
      }
    }
  });
}

}
