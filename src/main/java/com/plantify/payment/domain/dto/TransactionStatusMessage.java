package com.plantify.payment.domain.dto;

public record TransactionStatusMessage(
        Long transactionId,
        Long userId,
        Long amount,
        String status
) {}
