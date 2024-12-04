package com.plantify.payment.domain.dto;

import com.plantify.payment.domain.entity.Method;
import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;

public record TransactionRequest(
        Long userId,
        Long payId,
        Long sellerId,
        Long orderId,
        String orderName,
        Long amount,
        Method method,
        String transactionType,
        String reason
) {
    public Payment toEntity() {
        return Payment.builder()
                .userId(userId)
                .payId(payId)
                .sellerId(sellerId)
                .orderId(orderId)
                .orderName(orderName)
                .amount(amount)
                .method(method)
                .status(Status.PENDING)
                .build();
    }
}