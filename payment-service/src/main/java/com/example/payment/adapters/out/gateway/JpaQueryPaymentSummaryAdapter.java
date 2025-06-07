package com.example.payment.adapters.out.gateway;

//import com.example.payment.adapters.in.rest.PaymentSummaryResponse;
import com.example.payment.adapters.out.gateway.repository.PaymentRepository;
import com.example.payment.ports.out.QueryPaymentSummaryPort;
import com.rentacar.commons.dto.PaymentSummaryResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class JpaQueryPaymentSummaryAdapter implements QueryPaymentSummaryPort {

    private final PaymentRepository paymentRepository;

    public JpaQueryPaymentSummaryAdapter(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<PaymentSummaryResponse> sumGroupedByStatus(LocalDate from, LocalDate to) {
        return paymentRepository.sumAmountGroupedByStatusInDateRange(from, to);
    }

    @Override
    public List<PaymentSummaryResponse> sumGroupedByDate(LocalDate from, LocalDate to) {
        return paymentRepository.sumAmountGroupedByDateInRange(from, to);
    }
}
