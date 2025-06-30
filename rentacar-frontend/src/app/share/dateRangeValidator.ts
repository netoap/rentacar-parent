import { AbstractControl, ValidationErrors } from '@angular/forms';

export function dateRangeValidator(control: AbstractControl): ValidationErrors | null {
  const start = control.get('startDate')?.value;
  const end = control.get('endDate')?.value;

  if (!start || !end) return null;

  return new Date(start) <= new Date(end)
    ? null
    : { invalidDateRange: true };
}
