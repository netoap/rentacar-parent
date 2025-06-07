package com.example.payment.application;

import com.example.payment.adapters.in.rest.CreatePaymentRequest;
import com.example.payment.adapters.out.gateway.repository.PaymentRepository;
import com.example.payment.domain.PaymentEntity;
import com.example.payment.domain.PaymentStatus;
import com.example.payment.ports.in.CreatePaymentUseCase;
import com.example.payment.ports.out.PaymentGatewayPort;
import com.example.payment.ports.out.ReservationClientPort;
import com.example.payment.ports.out.SavePaymentPort;
import com.rentacar.commons.dto.CustomerPaymentHistoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService implements CreatePaymentUseCase {

    private final SavePaymentPort savePaymentPort;
    private final PaymentGatewayPort paymentGatewayPort;
    private final ReservationClientPort reservationClientPort;
    private final PaymentRepository paymentRepository;

    public PaymentService(SavePaymentPort savePaymentPort, PaymentGatewayPort paymentGatewayPort, ReservationClientPort reservationClientPort, PaymentRepository paymentRepository) {
        this.savePaymentPort = savePaymentPort;
        this.paymentGatewayPort = paymentGatewayPort;
        this.reservationClientPort = reservationClientPort;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentEntity createPayment(CreatePaymentRequest request) {
        PaymentEntity payment = new PaymentEntity(
                request.getCustomerId(),
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

    public CustomerPaymentHistoryResponse getPaymentsForCustomer(
            Long customerId, String statusStr, LocalDate fromDate, LocalDate toDate, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        PaymentStatus status = statusStr != null ? PaymentStatus.valueOf(statusStr) : null;

        Page<PaymentEntity> payments = paymentRepository.findPaymentsForCustomer(customerId, status, fromDate, toDate, pageable);

        List<CustomerPaymentHistoryResponse.PaymentSummary> summaries = payments.stream()
                .map(p -> new CustomerPaymentHistoryResponse.PaymentSummary(
                        p.getReservationId(),
                        p.getAmount(),
                        p.getStatus().name(),
                        p.getCreatedAt().toLocalDate()))
                .toList();

        BigDecimal totalAmount = summaries.stream()
                .map(CustomerPaymentHistoryResponse.PaymentSummary::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CustomerPaymentHistoryResponse(customerId,
                totalAmount,
                summaries,
                payments.getTotalPages(),
                payments.getTotalElements(),
                payments.getNumber(),
                payments.getSize());
    }

}
