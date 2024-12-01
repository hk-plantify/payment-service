package com.plantify.payment.domain.dto;

import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;

public record TransactionResponse(
        Long transactionId,
        Long userId,
        Long amount,
        Status status,
        String failureReason
) {
    public static TransactionResponse from(Payment payment) {
        return new TransactionResponse(
                payment.getPaymentId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getFailureReason()
        );
    }
}