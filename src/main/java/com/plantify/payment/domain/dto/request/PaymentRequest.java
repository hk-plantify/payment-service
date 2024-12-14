package com.plantify.payment.domain.dto.request;

import com.plantify.payment.domain.entity.Method;
import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;

public record PaymentRequest(
        Long userId,
        Long transactionId,
        String orderId,
        String orderName,
        Long amount
) {

    public Payment toEntity() {
        return Payment.builder()
                .userId(userId)
                .transactionId(transactionId)
                .orderId(orderId)
                .orderName(orderName)
                .amount(amount)
                .method(Method.PAY)
                .status(Status.PENDING)
                .build();
    }
}