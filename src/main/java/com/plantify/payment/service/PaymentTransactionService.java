package com.plantify.payment.service;

import com.plantify.payment.domain.dto.response.PaymentResponse;
import com.plantify.payment.domain.dto.request.PaymentRequest;

public interface PaymentTransactionService {

    PaymentResponse processPayment(PaymentRequest request);
}
