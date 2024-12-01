package com.plantify.payment.domain.dto.request;

import com.plantify.payment.domain.entity.Status;

public record PaymentAdminRequest(
        Status status,
        String reason
) {}
