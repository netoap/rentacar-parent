package com.example.payment.adapters.out.jpa;

public class JpaPaymentMapper {

    public static PaymentEntity toEntity(Payment domain) {
        PaymentEntity entity = new PaymentEntity();
        entity.setId(domain.getId());
        entity.setReservationId(domain.getReservationId());
        entity.setAmount(domain.getAmount());
        entity.setMethod(domain.getMethod());
        entity.setStatus(domain.getStatus());
        entity.setDate(domain.getDate());
        return entity;
    }

    public static Payment toDomain(PaymentEntity entity) {
        Payment domain = new Payment();
        domain.setId(entity.getId());
        domain.setReservationId(entity.getReservationId());
        domain.setAmount(entity.getAmount());
        domain.setMethod(entity.getMethod());
        domain.setStatus(entity.getStatus());
        domain.setDate(entity.getDate());
        return domain;
    }
}
