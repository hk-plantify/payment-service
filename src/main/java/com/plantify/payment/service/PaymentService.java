package com.plantify.payment.service;

import com.plantify.payment.domain.dto.request.PaymentRequest;
import com.plantify.payment.domain.dto.request.PaymentUpdateRequest;
import com.plantify.payment.domain.dto.response.PaymentResponse;
import com.plantify.payment.domain.entity.Status;

import java.util.List;

public interface PaymentService {

    List<PaymentResponse> getAllPayments();
    PaymentResponse getPayment(Long paymentId);
    PaymentResponse createPayment(PaymentRequest request);
    PaymentResponse updatePaymentStatus(Long paymentId, PaymentUpdateRequest request);
    List<PaymentResponse> getPaymentsByUser(Long userId);
}
