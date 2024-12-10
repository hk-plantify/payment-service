package com.plantify.payment.domain.dto.response;

import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;

import java.time.LocalDateTime;

public record PaymentAdminResponse(
        Long paymentId,
        Long userId,
        Long sellerId,
        String orderId,
        Long amount,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PaymentAdminResponse from(Payment payment) {
        return new PaymentAdminResponse(
                payment.getPaymentId(),
                payment.getUserId(),
                payment.getSellerId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}