package com.rentacar.commons.dto;

public class ReservationStatusUpdateRequest {
    private String status;

    public ReservationStatusUpdateRequest(String status) {
        this.status = status;
    }

    public String getStatus() { return status; }
}
