package com.example.payment.adapters.in.rest;

import com.example.payment.ports.in.PaymentUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("api/v1/payments")
@Tag(name = "Billing", description = "Endpoints related to customer payments and billing")
public class PaymentController {
    private final PaymentUseCase paymentUseCase;

    public PaymentController(PaymentUseCase paymentUseCase) {
        this.paymentUseCase = paymentUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> processPayment(@RequestBody PaymentRequest request) {
        paymentUseCase.processPayment(request);
        return ResponseEntity.ok().build();
    }
}
