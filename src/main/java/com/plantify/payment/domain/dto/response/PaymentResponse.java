package com.plantify.payment.domain.dto.response;

import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;

import java.time.LocalDateTime;

public record PaymentResponse(
        Long paymentId,
        Status status,
        String method,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getStatus(),
                payment.getMethod().name(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}