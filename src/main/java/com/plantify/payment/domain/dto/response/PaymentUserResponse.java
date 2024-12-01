package com.plantify.payment.domain.dto.response;

import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;

import java.time.LocalDateTime;

public record PaymentUserResponse(
        Long paymentId,
        Long userId,
        Long orderId,
        Long amount,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PaymentUserResponse from(Payment payment) {
        return new PaymentUserResponse(
                payment.getPaymentId(),
                payment.getUserId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}