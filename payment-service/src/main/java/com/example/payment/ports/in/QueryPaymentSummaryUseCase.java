package com.example.payment.ports.in;


import com.rentacar.commons.dto.PaymentSummaryResponse;

import java.time.LocalDate;
import java.util.List;

public interface QueryPaymentSummaryUseCase {
    List<PaymentSummaryResponse> getSummary(String groupBy, LocalDate fromDate, LocalDate toDate);
}
