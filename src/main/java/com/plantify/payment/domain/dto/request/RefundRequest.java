package com.plantify.payment.domain.dto.request;

public record RefundRequest (
        Long paymentId,
        String reason
) {
}
