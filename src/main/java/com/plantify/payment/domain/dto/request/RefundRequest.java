package com.plantify.payment.domain.dto.request;

public record RefundRequest (
        Long userId,
        Long paymentId,
        String reason
) {
}
