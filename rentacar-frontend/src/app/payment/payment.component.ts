/* eslint-disable */
import { Component, inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-payment',
    imports: [
      CommonModule, 
    MatCardModule,
    MatIconModule
  ],
  standalone: true,
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss']
})
export class PaymentComponent implements OnInit {
  reservations: any[] = [];
  email: string = '';
  private http= inject(HttpClient);
  private route= inject(ActivatedRoute);
  private router= inject(Router);

  

  ngOnInit(): void {
  this.route.queryParams.subscribe(params => {
    this.email = params['email'];
    if (this.email) {
      this.fetchPendingReservations();
    } else {
      alert('Email no proporcionado');
      this.router.navigate(['/']);
    }
  });
}


  fetchPendingReservations(): void {
    this.http.get<any[]>(`http://localhost:8080/api/v1/reservations/pending?email=${this.email}`)
      .subscribe({
      next: data => this.reservations = data,
      error: err => {
        console.error('Error cargando reservas:', err);
        alert('Error cargando reservas pendientes');
      }
    });
  }

  pay(reservationId: number): void {
    this.http.post(`http://localhost:8080/api/v1/payments`, {
      reservationId,
      amount: 100.00, // puedes calcular o traer el valor real
      method: 'CARD'  // o 'CASH' por ejemplo
    }).subscribe(() => {
      alert('Pago realizado con éxito');
      this.router.navigate(['/my-reservations']);
    });
  }
}
