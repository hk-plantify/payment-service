package com.plantify.payment.domain.dto.request;

public record CancellationRequest(
        Long paymentId,
        String reason
) {
}