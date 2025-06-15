import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { AbstractControl, ValidationErrors } from '@angular/forms';
@Component({
  selector: 'app-reservation-form',
  standalone: true,
  templateUrl: './reservation-form.component.html',
  styleUrls: ['./reservation-form.component.scss'],
  imports: [
    ReactiveFormsModule,
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatCardModule,
    MatButtonModule
  ]
})
export class ReservationFormComponent implements OnInit {
  form!: FormGroup;
  vehicleId!: number;
  minDate = new Date();
  bookedDates: Set<string> = new Set();

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private http: HttpClient,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.vehicleId = +this.route.snapshot.paramMap.get('vehicleId')!;

    const today = new Date();
    const end = new Date();
    end.setDate(today.getDate() + 3);

    this.form = this.fb.group(
      {
        startDate: [today, Validators.required],
        endDate: [end, Validators.required]
      },
      { validators: dateRangeValidator }
    );

    
    this.http
      .get<{ vehicleId: number; bookedDates: string[] }>(
        `/api/v1/reservations/vehicles/${this.vehicleId}/availability`
      )
      .subscribe((response) => {
        this.bookedDates = new Set(response.bookedDates);
      });
  }

  // Updated date filter to block booked and past dates
  dateFilter = (d: Date | null): boolean => {
    if (!d) return false;
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const dateString = d.toISOString().split('T')[0];
    return d >= today && !this.bookedDates.has(dateString);
  };

  submit(): void {
    if (this.form.invalid) return;

    const customerId = this.getCustomerIdFromToken();
    const { startDate, endDate } = this.form.value;

    this.http
      .post('/api/v1/reservations', {
        customerId,
        vehicleId: this.vehicleId,
        startDate,
        endDate
      })
      .subscribe({
        next: () => {
          this.snackBar.open('Reservation created!', 'Close', { duration: 3000 });
          this.router.navigate(['/vehicles']);
        },
        error: () => {
          this.snackBar.open('Reservation failed. Try again.', 'Close', { duration: 3000 });
        }
      });
  }

  private getCustomerIdFromToken(): number {
    const token = localStorage.getItem('token');
    if (!token) return 0;
    const payload = JSON.parse(atob(token.split('.')[1]));
    return +payload.sub;
  }

  handleDateChange(event: any): void {
    const selected: Date = event.value;
    if(!selected) return;

    const isoDate = selected.toISOString().split('T')[0];

    if (this.bookedDates.has(isoDate)) {
      this.snackBar.open(`${isoDate} is already booked`, 'Close', {
        duration: 3000
      });

      event.source.value = null;
      const ctrlName = event.targetElement.getAttribute('formcontrolname');
      this.form.get(ctrlName)?.setValue(null);
    }
  }
}

function dateRangeValidator(control: AbstractControl): ValidationErrors | null {
  const start = control.get('startDate')?.value;
  const end = control.get('endDate')?.value;

  if (start && end && end < start) {
    return { dateRangeInvalid: true };
  }
  return null;
}


