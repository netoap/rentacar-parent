import { Component } from '@angular/core';
import { Router,} from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';

@Component({
  selector: 'app-landing',
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    CommonModule
  ],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.scss'
})
export class LandingComponent  {
  today = new Date();
  searchForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router) {
    this.searchForm = this.fb.group({
      location: ['', Validators.required],
      startDate: [this.today, Validators.required],
      endDate: [new Date(this.today.getTime() + 3 * 864e5), Validators.required]
    });
  }

  search(): void {
  if (this.searchForm.invalid) return;

  const { location, startDate, endDate } = this.searchForm.value;

  // Navigate to /vehicles with query params
  this.router.navigate(['/vehicles'], {
    queryParams: {
      location,
      start: startDate.toISOString().split('T')[0],
      end: endDate.toISOString().split('T')[0]
    }
  });
}


}
