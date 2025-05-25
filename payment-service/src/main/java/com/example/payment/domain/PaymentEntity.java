package com.example.payment.domain;

import jakarta.persistence.*;
        import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reservationId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false)
    private String method; // e.g., "CREDIT_CARD", "PAYPAL", "BANK_TRANSFER"

    private LocalDateTime createdAt;

    public PaymentEntity() {}

    public PaymentEntity(Long reservationId, BigDecimal amount, String method, PaymentStatus status) {
        this.reservationId = reservationId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getReservationId() { return reservationId; }
    public BigDecimal getAmount() { return amount; }
    public String getMethod() { return method; }
    public PaymentStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setStatus(PaymentStatus status) { this.status = status; }
}
