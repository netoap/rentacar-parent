import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCheckboxModule } from '@angular/material/checkbox'; // ✅ importante
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-create-vehicle',
  standalone: true,
  templateUrl: './create-vehicle.component.html',
  styleUrls: ['./create-vehicle.component.scss'],
  imports: [
        CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule, // ✅ necesario para usar <mat-checkbox>
    MatButtonModule
  ]
})
export class CreateVehicleComponent {
  form: FormGroup;
 fb= inject(FormBuilder);
    http= inject(HttpClient);
    snackBar= inject(MatSnackBar);
    router= inject(Router);
  constructor() {
    this.form = this.fb.group({
      model: ['', Validators.required],
      year: ['', [Validators.required, Validators.min(1900)]],
      available: [true]
    });
  }

  onSubmit(): void {
    if (this.form.invalid) return;

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    this.http.post(`${environment.apiBaseUrl}/vehicles`, this.form.value, { headers })
      .subscribe({
        next: () => {
          this.snackBar.open('Vehículo creado con éxito', 'Cerrar', { duration: 3000 });
          this.router.navigate(['/admin/vehicles']);
        },
        error: () => {
          this.snackBar.open('Error al crear el vehículo', 'Cerrar', { duration: 3000 });
        }
      });
  }
}
