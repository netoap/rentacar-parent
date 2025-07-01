package com.example.reservation.adapters.out.jpa;

import com.example.reservation.adapters.out.jpa.entity.ReservationEntity;
import com.rentacar.commons.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SpringDataReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByCustomerEmail(String email);

//    @Query("SELECT r FROM ReservationEntity r WHERE r.carId = :vehicleId AND r.status = 'CONFIRMED' AND r.startDate <= :end AND r.endDate >= :start")
//    List<ReservationEntity> findOverlappingReservations(Long vehicleId, LocalDate start, LocalDate end);

    @Query("""
    SELECT COUNT(r) > 0
    FROM ReservationEntity r
    WHERE r.carId = :carId
      AND r.status IN ('CONFIRMED', 'PENDING')
      AND r.startDate <= :endDate
      AND r.endDate >= :startDate
""")
    boolean isOverlappingReservation(
            @Param("carId") Long carId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    boolean existsByCarIdAndStatusInAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long carId,
            List<ReservationStatus> status,
            LocalDate endDate,
            LocalDate startDate
    );

    List<ReservationEntity> findByCustomerEmailAndStatus(String email, String status);

    List<ReservationEntity>  findByCustomerName(String username);
}
