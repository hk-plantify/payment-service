package com.plantify.payment.service;

import com.plantify.payment.domain.dto.request.PaymentAdminRequest;
import com.plantify.payment.domain.dto.response.PaymentAdminResponse;

import java.util.List;

public interface PaymentAdminService {

    List<PaymentAdminResponse> getAllPayments();
    PaymentAdminResponse getPaymentById(Long paymentId);
    PaymentAdminResponse updatePaymentStatus(Long paymentId, PaymentAdminRequest request);
    List<PaymentAdminResponse> getPaymentsByUserId(Long userId);
}
