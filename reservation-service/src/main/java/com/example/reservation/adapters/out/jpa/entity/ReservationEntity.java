package com.example.reservation.adapters.out.jpa.entity;

import com.rentacar.commons.ReservationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_email", nullable = false)
    @NotNull(message = "Customer email must not be null")
    private String customerEmail;

    private Long carId;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public ReservationEntity() {
    }

//    public ReservationEntity(String customerEmail, Long carId, LocalDate startDate, LocalDate endDate, ReservationStatus status) {
//        this.customerEmail = customerEmail;
//        this.carId = carId;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.status = status;
//    }
//
//    public ReservationEntity(Long id, String customerEmail, Long carId, LocalDate startDate, LocalDate endDate, ReservationStatus status) {
//        this.id = id;
//        this.customerEmail = customerEmail;
//        this.carId = carId;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.status = status;
//    }

    // Getters y setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
}
