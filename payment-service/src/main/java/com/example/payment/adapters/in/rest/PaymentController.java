package com.example.payment.adapters.in.rest;

import com.example.payment.domain.PaymentEntity;
import com.example.payment.ports.in.CreatePaymentUseCase;
import com.example.payment.ports.in.QueryPaymentSummaryUseCase;
import com.example.payment.ports.out.LoadPaymentPort;
import com.rentacar.commons.dto.PagedResponse;
import com.rentacar.commons.dto.PaymentSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/payments")
@Tag(name = "Billing", description = "Endpoints related to customer payments and billing")
public class PaymentController {

    private final CreatePaymentUseCase createPaymentUseCase;
    private final LoadPaymentPort loadPaymentPort;
    private final QueryPaymentSummaryUseCase queryPaymentSummaryUseCase;

    public PaymentController(CreatePaymentUseCase createPaymentUseCase, LoadPaymentPort loadPaymentPort, QueryPaymentSummaryUseCase queryPaymentSummaryUseCase) {
        this.createPaymentUseCase = createPaymentUseCase;
        this.loadPaymentPort = loadPaymentPort;
        this.queryPaymentSummaryUseCase = queryPaymentSummaryUseCase;
    }

    @PostMapping
    @Operation(summary = "Create a new payment for a reservation")
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        PaymentEntity payment = createPaymentUseCase.createPayment(request);
        URI location = URI.create("/payments/" + payment.getId());
        return ResponseEntity.created(location).body(toResponse(payment));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id) {
        return loadPaymentPort.findById(id)
                .map(payment -> ResponseEntity.ok(toResponse(payment)))
                .orElse(ResponseEntity.notFound().build());
    }

    //GET /payments?status=PAID&reservationId=101&page=0&size=5

    @Parameters({
            @Parameter(name = "status", description = "Filter by payment status (e.g., PAID, FAILED)"),
            @Parameter(name = "reservationId", description = "Filter by reservation ID"),
            @Parameter(name = "createdAtFrom", description = "Filter by payments made on or after this date (YYYY-MM-DD)"),
            @Parameter(name = "createdAtTo", description = "Filter by payments made on or before this date (YYYY-MM-DD)"),
            @Parameter(name = "page", description = "Page number (0-based)", example = "0"),
            @Parameter(name = "size", description = "Page size", example = "10"),
            @Parameter(name = "sort", description = "Sort format: field,direction (e.g., createdAt,desc)", example = "createdAt,desc")
    })
    @GetMapping
    @Operation(summary = "List payments with filters, dates, sorting",
            security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<PagedResponse<PaymentResponse>> findPayments(
            @ModelAttribute PaymentSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort[1]), sort[0]));
        Page<PaymentEntity> results = loadPaymentPort.findAll(criteria, pageable);

        List<PaymentResponse> responses = results.getContent().stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(new PagedResponse<>(
                page, size, results.getTotalElements(), results.getTotalPages(), responses
        ));
    }

    //GET /payments/summary?groupBy=date&fromDate=2024-01-01&toDate=2024-12-31

    @GetMapping("/summary")
    @Operation(
            summary = "Get total payment amounts grouped by status or date",
            description = "Supports grouping by status or date, and filtering by fromDate/toDate"
    )
    @Parameters({
            @Parameter(name = "groupBy", description = "Group by 'status' or 'date'", example = "status"),
            @Parameter(name = "fromDate", description = "Include payments from this date (YYYY-MM-DD)", example = "2024-01-01"),
            @Parameter(name = "toDate", description = "Include payments up to this date (YYYY-MM-DD)", example = "2024-12-31"),
            @Parameter(name = "page", description = "Page number (0-based)", example = "0"),
            @Parameter(name = "size", description = "Page size", example = "10")
    })
    public ResponseEntity<PagedResponse<PaymentSummaryResponse>> getPaymentSummary(
            @RequestParam(defaultValue = "status") String groupBy,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        List<PaymentSummaryResponse> fullList = queryPaymentSummaryUseCase.getSummary(groupBy, fromDate, toDate);

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), fullList.size());
        List<PaymentSummaryResponse> pagedContent = fullList.subList(start, end);

        return ResponseEntity.ok(new PagedResponse<>(
                page, size, fullList.size(),
                (int) Math.ceil((double) fullList.size() / size),
                pagedContent
        ));
    }

    private PaymentResponse toResponse(PaymentEntity payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getReservationId(),
                payment.getAmount(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getCreatedAt()
        );
    }
}
