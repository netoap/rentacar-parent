package com.example.payment.application;

import com.example.payment.adapters.in.rest.PaymentRequest;
import com.example.payment.adapters.out.client.ReservationClient;
import com.example.payment.adapters.out.jpa.Payment;
import com.example.payment.ports.in.PaymentUseCase;
import com.example.payment.ports.out.SavePaymentPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService implements PaymentUseCase {

    private final SavePaymentPort savePaymentPort;
    private final ReservationClient reservationClient;

    public PaymentService(SavePaymentPort savePaymentPort, ReservationClient reservationClient) {
        this.savePaymentPort = savePaymentPort;
        this.reservationClient = reservationClient;
    }


    @Override
    public void processPayment(PaymentRequest request) {
            Payment payment = new Payment();
            payment.setReservationId(request.getReservationId());
            payment.setAmount(request.getAmount());
            payment.setMethod(request.getMethod());
            payment.setDate(LocalDateTime.now());
            payment.setStatus("PAID");

            savePaymentPort.save(payment);

            reservationClient.markAsPaid(request.getReservationId());
    }
}
