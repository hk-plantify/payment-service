package com.plantify.payment.domain.dto.response;

import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;

import java.time.LocalDateTime;

public record PaymentResponse(
        Long paymentId,
        Long userId,
        Long sellerId,
        Status status,
        String failureReason,
        Long amount,
        Long orderId,
        String orderName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getUserId(),
                payment.getSellerId(),
                payment.getStatus(),
                payment.getFailureReason(),
                payment.getAmount(),
                payment.getOrderId(),
                payment.getOrderName(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}
