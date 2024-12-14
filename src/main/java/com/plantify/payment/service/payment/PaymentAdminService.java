package com.plantify.payment.service.payment;

import com.plantify.payment.domain.dto.response.ProcessResponse;

import java.util.List;

public interface PaymentAdminService {

    List<ProcessResponse> getAllPayments();
    ProcessResponse getPaymentById(Long paymentId);
    List<ProcessResponse> getPaymentsByUserId(Long userId);
}
