package com.rentacar.commons.dto;

import java.time.LocalDate;
import java.util.List;

public class VehicleAvailabilityResponse {
    private Long vehicleId;
    private List<LocalDate> bookedDates;

    public VehicleAvailabilityResponse(Long vehicleId, List<LocalDate> bookedDates) {
        this.vehicleId = vehicleId;
        this.bookedDates = bookedDates;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public List<LocalDate> getBookedDates() {
        return bookedDates;
    }
}
