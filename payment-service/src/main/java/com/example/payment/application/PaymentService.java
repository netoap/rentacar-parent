package com.example.payment.application;

import com.example.payment.adapters.in.rest.CreatePaymentRequest;
import com.example.payment.domain.PaymentEntity;
import com.example.payment.domain.PaymentStatus;
import com.example.payment.ports.in.CreatePaymentUseCase;
import com.example.payment.ports.out.PaymentGatewayPort;
import com.example.payment.ports.out.ReservationClientPort;
import com.example.payment.ports.out.SavePaymentPort;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements CreatePaymentUseCase {

    private final SavePaymentPort savePaymentPort;
    private final PaymentGatewayPort paymentGatewayPort;
    private final ReservationClientPort reservationClientPort;

    public PaymentService(SavePaymentPort savePaymentPort, PaymentGatewayPort paymentGatewayPort, ReservationClientPort reservationClientPort) {
        this.savePaymentPort = savePaymentPort;
        this.paymentGatewayPort = paymentGatewayPort;
        this.reservationClientPort = reservationClientPort;
    }

    @Override
    public PaymentEntity createPayment(CreatePaymentRequest request) {
        PaymentEntity payment = new PaymentEntity(
                request.getReservationId(),
                request.getAmount(),
                request.getMethod(),
                PaymentStatus.PENDING
        );

        PaymentEntity saved = savePaymentPort.save(payment);

        // Process via gateway
        boolean approved = paymentGatewayPort.processPayment(saved);


        // Update status and save again
        saved.setStatus(approved ? PaymentStatus.PAID : PaymentStatus.FAILED);
        PaymentEntity finalPayment = savePaymentPort.save(saved);
        // If paid, notify reservation-service
        if (approved) {
            reservationClientPort.updateReservationStatus(
                    finalPayment.getReservationId(), "PAID"
            );
        }
        return finalPayment;
    }
}
