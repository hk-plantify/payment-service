package com.plantify.payment.domain.dto;

import com.plantify.payment.domain.entity.Status;

public record TransactionStatusMessage(
        Long transactionId,
        Long userId,
        Long orderId,
        Long amount,
        Status status
){
}