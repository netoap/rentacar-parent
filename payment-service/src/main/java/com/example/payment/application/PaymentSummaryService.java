package com.example.payment.application;

import com.example.payment.ports.in.QueryPaymentSummaryUseCase;
import com.example.payment.ports.out.QueryPaymentSummaryPort;
import com.rentacar.commons.dto.PaymentSummaryResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentSummaryService implements QueryPaymentSummaryUseCase {

    private final QueryPaymentSummaryPort summaryPort;

    public PaymentSummaryService(QueryPaymentSummaryPort summaryPort) {
        this.summaryPort = summaryPort;
    }

    @Override
    public List<PaymentSummaryResponse> getSummary(String groupBy, LocalDate fromDate, LocalDate toDate) {
        return switch (groupBy.toLowerCase()) {
            case "date" -> summaryPort.sumGroupedByDate(fromDate, toDate);
            case "status" -> summaryPort.sumGroupedByStatus(fromDate, toDate);
            default -> throw new IllegalArgumentException("Invalid groupBy value: " + groupBy);
        };
    }
}
