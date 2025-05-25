package com.rentacar.commons.dto;

import java.math.BigDecimal;

public record PaymentSummaryResponse(String group, BigDecimal totalAmount) {}

