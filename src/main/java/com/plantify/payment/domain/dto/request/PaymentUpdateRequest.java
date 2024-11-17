package com.plantify.payment.domain.dto.request;

import com.plantify.payment.domain.entity.Status;

public record PaymentUpdateRequest(Status status) {
}
