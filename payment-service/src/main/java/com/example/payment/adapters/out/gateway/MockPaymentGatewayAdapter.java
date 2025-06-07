package com.example.payment.adapters.out.gateway;

import com.example.payment.domain.PaymentEntity;
import com.example.payment.ports.out.PaymentGatewayPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

@Component
public class MockPaymentGatewayAdapter implements PaymentGatewayPort {

    private final Random random = new Random();

    @Override
    public boolean processPayment(PaymentEntity payment) {
        // Simulate payment approval for small amounts
        if (payment.getAmount().compareTo(BigDecimal.valueOf(100)) <= 0) {
            return true;
        }

        // Randomly approve or reject high-value transactions
        return random.nextBoolean();
    }
}
