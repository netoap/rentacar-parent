import { Component } from '@angular/core';
import { RouterOutlet, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  imports: [
    CommonModule,
    RouterOutlet,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatButtonModule
  ]
})
export class AppComponent {
  today = new Date();
  searchForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router) {
    this.searchForm = this.fb.group({
      location: ['', Validators.required],
      startDate: [this.today, Validators.required],
      endDate: [new Date(this.today.getTime() + 3 * 864e5), Validators.required],
    }, {
      validators: (c) => {
        const s = c.get('startDate')!.value;
        const e = c.get('endDate')!.value;
        return e >= s ? null : { dateRange: true };
      }
    });
  }

  search() {
    if (this.searchForm.invalid) return;

    const { location, startDate, endDate } = this.searchForm.value;

    //  Navigate to /vehicles with query params
    this.router.navigate(['/vehicles'], {
      queryParams: {
        location,
        start: startDate.toISOString().split('T')[0],
        end: endDate.toISOString().split('T')[0]
      }
    });
  }
}
