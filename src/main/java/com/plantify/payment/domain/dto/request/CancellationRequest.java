package com.plantify.payment.domain.dto.request;

public record CancellationRequest(
        Long userId,
        Long paymentId,
        String reason
) {
}