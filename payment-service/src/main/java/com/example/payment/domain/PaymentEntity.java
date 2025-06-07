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

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long reservationId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String method; // e.g., "CREDIT_CARD", "PAYPAL", "BANK_TRANSFER"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    private LocalDateTime createdAt;


//    @ManyToOne(optional = false)
//    private Reservation reservation;

//    @ManyToOne(optional = false)
//    private CustomerEntity customer;

    public PaymentEntity() {}

    public PaymentEntity(Long customerId, Long reservationId, BigDecimal amount, String method, PaymentStatus status) {

        this.customerId = customerId;
        this.reservationId = reservationId;
        this.amount = amount;
        this.method = method;
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getReservationId() { return reservationId; }
    public BigDecimal getAmount() { return amount; }
    public String getMethod() { return method; }
    public PaymentStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setStatus(PaymentStatus status) { this.status = status; }


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
