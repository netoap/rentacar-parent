package com.example.payment.adapters.out.gateway;

import com.example.payment.adapters.in.rest.PaymentSearchCriteria;
import com.example.payment.adapters.out.gateway.repository.PaymentRepository;
import com.example.payment.domain.PaymentEntity;
import com.example.payment.ports.out.LoadPaymentPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JpaPaymentQueryAdapter implements LoadPaymentPort {

    private final PaymentRepository paymentRepository;

    public JpaPaymentQueryAdapter(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Optional<PaymentEntity> findById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Page<PaymentEntity> findAll(PaymentSearchCriteria criteria, Pageable pageable) {
        Specification<PaymentEntity> spec = Specification
                .where(PaymentSpecifications.hasStatus(criteria.getStatus()))
                .and(PaymentSpecifications.hasReservationId(criteria.getReservationId()))
                .and(PaymentSpecifications.createdAtAfter(criteria.getCreatedAtFrom()))
                .and(PaymentSpecifications.createdAtBefore(criteria.getCreatedAtTo()));

        return paymentRepository.findAll(spec, pageable);
    }
}
