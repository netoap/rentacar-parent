import { Component, inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { Payment } from '../share/models';

@Component({
  selector: 'app-payment-list',
  standalone: true,
  imports: [CommonModule, MatCardModule ],
  templateUrl: './payment-list.component.html',
  styleUrls: ['./payment-list.component.scss'],
})

export class PaymentListComponent implements OnInit {
  payments: Payment[] = [];
  http = inject(HttpClient);

  ngOnInit(): void {
    this.loadPayments();
  }

  loadPayments(): void {
    this.http.get<Payment[]>(`${environment.apiBaseUrl}/payments/mine`)
      .subscribe(data => {
        this.payments = data;
      });
  }
}
