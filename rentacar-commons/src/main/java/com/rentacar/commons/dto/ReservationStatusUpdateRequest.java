package com.rentacar.commons.dto;

public class ReservationStatusUpdateRequest {
    private String status;

    public ReservationStatusUpdateRequest() {}

    public ReservationStatusUpdateRequest(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
