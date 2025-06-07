package com.example.payment.adapters.out.gateway.repository;

import com.example.payment.domain.PaymentEntity;
import com.example.payment.domain.PaymentStatus;
import com.rentacar.commons.dto.PaymentSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long>, JpaSpecificationExecutor<PaymentEntity> {

    @Query("""
                SELECT new com.rentacar.commons.dto.PaymentSummaryResponse(p.status, SUM(p.amount))
                FROM PaymentEntity p
                WHERE (:from IS NULL OR p.createdAt >= :from)
                  AND (:to IS NULL OR p.createdAt <= :to)
                GROUP BY p.status
            """)
    List<PaymentSummaryResponse> sumAmountGroupedByStatusInDateRange(LocalDate from, LocalDate to);

    @Query("""
                SELECT new com.rentacar.commons.dto.PaymentSummaryResponse(CAST(p.createdAt AS date), SUM(p.amount))
                FROM PaymentEntity p
                WHERE (:from IS NULL OR p.createdAt >= :from)
                  AND (:to IS NULL OR p.createdAt <= :to)
                GROUP BY CAST(p.createdAt AS date)
            """)
    List<PaymentSummaryResponse> sumAmountGroupedByDateInRange(LocalDate from, LocalDate to);

    @Query("""
            SELECT p FROM Payment p
            WHERE p.customerId = :customerId
            AND (:status IS NULL OR p.status = :status)
            AND (:fromDate IS NULL OR p.date >= :fromDate)
            AND (:toDate IS NULL OR p.date <= :toDate)
            """)
    Page<PaymentEntity> findPaymentsForCustomer(
            @Param("customerId") Long customerId,
            @Param("status") PaymentStatus status,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            Pageable pageable
    );

}
