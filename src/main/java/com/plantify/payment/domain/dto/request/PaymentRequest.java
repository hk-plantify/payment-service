package com.plantify.payment.domain.dto.request;

import com.plantify.payment.domain.entity.Method;
import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;

public record PaymentRequest(
        Long userId,
        String orderId,
        String orderName,
        Long sellerId,
        Long amount
) {

    public Payment toEntity() {
        return Payment.builder()
                .userId(userId)
                .orderId(orderId)
                .orderName(orderName)
                .sellerId(sellerId)
                .amount(amount)
                .method(Method.PAY)
                .status(Status.PENDING)
                .build();
    }
}