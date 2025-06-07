package com.rentacar.commons.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Customer payment history with pagination")
public class CustomerPaymentHistoryResponse {
    @Schema(description = "Customer ID", example = "123")
    private Long customerId;
    @Schema(description = "Total amount paid or pending in this result set", example = "250.00")
    private BigDecimal totalAmount;
    @Schema(description = "List of payments")
    private List<PaymentSummary> payments;
    @Schema(description = "Total number of result pages", example = "5")
    private int totalPages;
    @Schema(description = "Total number of results", example = "42")
    private long totalElements;
    @Schema(description = "Current page number", example = "0")
    private int currentPage;
    @Schema(description = "Page size used", example = "10")
    private int size;



    public CustomerPaymentHistoryResponse() {}

    public CustomerPaymentHistoryResponse(Long customerId, BigDecimal totalAmount,
                                          List<PaymentSummary> payments,
                                          int totalPages, long totalElements, int currentPage, int size) {
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.payments = payments;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.size = size;
    }

    public static class PaymentSummary {
        @Schema(description = "Reservation ID", example = "1001")
        private Long reservationId;
        @Schema(description = "Payment amount", example = "100.00")
        private BigDecimal amount;
        @Schema(description = "Payment status", example = "PAID")
        private String status;
        @Schema(description = "Payment date", example = "2025-05-10")
        private LocalDate date;


        public PaymentSummary() {}

        public PaymentSummary(Long reservationId, BigDecimal amount, String status, LocalDate date) {
            this.reservationId = reservationId;

            this.amount = amount;
            this.status = status;
            this.date = date;
        }

        public Long getReservationId() {
            return reservationId;
        }

        public void setReservationId(Long reservationId) {
            this.reservationId = reservationId;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<PaymentSummary> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentSummary> payments) {
        this.payments = payments;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
