package com.example.payment.ports.out;

import com.rentacar.commons.dto.PaymentSummaryResponse;

import java.time.LocalDate;
import java.util.List;

public interface QueryPaymentSummaryPort {
    List<PaymentSummaryResponse> sumGroupedByStatus(LocalDate from, LocalDate to);
    List<PaymentSummaryResponse> sumGroupedByDate(LocalDate from, LocalDate to);
}
