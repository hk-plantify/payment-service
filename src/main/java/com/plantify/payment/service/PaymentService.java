package com.plantify.payment.service;

import com.plantify.payment.domain.dto.request.PaymentRequest;
import com.plantify.payment.domain.dto.response.PaymentResponse;
import com.plantify.payment.domain.entity.Status;

import java.util.List;

public interface PaymentService {

    List<PaymentResponse> getAllPayments();
    PaymentResponse createPayment(PaymentRequest request);
    PaymentResponse getPayment(Long paymentId);
    PaymentResponse cancelPayment(Long paymentId);
    PaymentResponse updatePayment(Long paymentId, Status status);
    List<PaymentResponse> getPaymentsByUser();
}
