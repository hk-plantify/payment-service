package com.plantify.payment.domain.dto.request;

import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;

public record PaymentRequest(
        Long payId,
        Long sellerId,
        Long amount,
        Long orderId,
        String orderName
) {

    public Payment toEntity(Long userId) {
        return Payment.builder()
                .userId(userId)
                .payId(payId)
                .sellerId(sellerId)
                .status(Status.PENDING)
                .amount(amount)
                .orderId(orderId)
                .orderName(orderName)
                .build();
    }
}
