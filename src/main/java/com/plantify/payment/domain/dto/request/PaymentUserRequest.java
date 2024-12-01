package com.plantify.payment.domain.dto.request;

import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;

public record PaymentUserRequest(
        Long sellerId,
        Long orderId,
        Long amount
) {
    public Payment toEntity(Long userId) {
        return Payment.builder()
                .userId(userId)
                .sellerId(sellerId)
                .orderId(orderId)
                .amount(amount)
                .status(Status.PENDING)
                .build();
    }
}
