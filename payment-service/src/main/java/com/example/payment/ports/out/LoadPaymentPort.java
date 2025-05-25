package com.example.payment.ports.out;

import com.example.payment.adapters.in.rest.PaymentSearchCriteria;
import com.example.payment.domain.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LoadPaymentPort {
    Optional<PaymentEntity> findById(Long id);
    Page<PaymentEntity> findAll(PaymentSearchCriteria criteria, Pageable pageable);
}
