import { Component } from '@angular/core';

@Component({
  selector: 'app-forbidden',
  standalone: true,
  template: `<h2>403 - Acceso denegado</h2><p>No tienes permisos para acceder a esta ruta.</p>`,
  styles: ['h2 { color: darkorange; }']
})
export class ForbiddenComponent {}
