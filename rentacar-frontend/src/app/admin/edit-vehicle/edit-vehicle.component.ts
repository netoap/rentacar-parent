import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from '../../../environments/environment';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-edit-vehicle',
  standalone: true,
  templateUrl: './edit-vehicle.component.html',
  styleUrls: ['./edit-vehicle.component.scss'],
  imports: [CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule, // ✅ aquí
    MatButtonModule
    // CommonModule, FormsModule, ReactiveFormsModule, Angular Material modules...
  ]
})
export class EditVehicleComponent implements OnInit {
  form: FormGroup;
  vehicleId!: number;
  route = inject(ActivatedRoute);
  fb = inject(FormBuilder);
  http = inject(HttpClient);
  snackBar = inject(MatSnackBar);
  router = inject(Router);

  constructor() {
    this.form = this.fb.group({
      model: ['', Validators.required],
      year: ['', [Validators.required, Validators.min(1900)]],
      available: [true]
    });
  }

  ngOnInit(): void {
    this.vehicleId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadVehicle();
  }

  loadVehicle(): void {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
// eslint-disable-next-line @typescript-eslint/no-explicit-any
    this.http.get<any>(`${environment.apiBaseUrl}/vehicles/${this.vehicleId}`, { headers })
      .subscribe({
        next: vehicle => this.form.patchValue(vehicle),
        error: () => this.snackBar.open('Error al cargar vehículo', 'Cerrar', { duration: 3000 })
      });
  }
  cancel(): void {
  this.router.navigate(['/admin/vehicles']);
}


  onSubmit(): void {
    if (this.form.invalid) return;

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    this.http.put(`${environment.apiBaseUrl}/vehicles/${this.vehicleId}`, this.form.value, { headers })
      .subscribe({
        next: () => {
          this.snackBar.open('Vehículo actualizado', 'Cerrar', { duration: 3000 });
          this.router.navigate(['/admin/vehicles']);
        },
        error: () => this.snackBar.open('Error al actualizar vehículo', 'Cerrar', { duration: 3000 })
      });
  }
}
