import { Component } from '@angular/core';

@Component({
  selector: 'app-not-found',
  standalone: true,
  template: `<h2>Página no encontrada</h2><p>Lo sentimos, la página solicitada no existe.</p>`,
  styles: ['h2 { color: red; }']
})
export class NotFoundComponent {}
