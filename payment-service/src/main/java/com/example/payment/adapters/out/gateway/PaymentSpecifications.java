package com.example.payment.adapters.out.gateway;

import com.example.payment.domain.PaymentEntity;
import com.example.payment.domain.PaymentStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PaymentSpecifications {

    public static Specification<PaymentEntity> hasStatus(PaymentStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<PaymentEntity> hasReservationId(Long reservationId) {
        return (root, query, cb) -> reservationId == null ? null : cb.equal(root.get("reservationId"), reservationId);
    }

    public static Specification<PaymentEntity> createdAtAfter(LocalDate from) {
        return (root, query, cb) -> from == null ? null : cb.greaterThanOrEqualTo(root.get("createdAt"), from.atStartOfDay());
    }

    public static Specification<PaymentEntity> createdAtBefore(LocalDate to) {
        return (root, query, cb) -> to == null ? null : cb.lessThanOrEqualTo(root.get("createdAt"), to.atTime(23, 59, 59));
    }
    public static Specification<PaymentEntity> hasCustomerId(Long customerId) {
        return (root, query, cb) -> customerId == null ? null :
                cb.equal(root.get("customerId"), customerId);
    }

}
