package com.plantify.payment.service.process;

import com.plantify.payment.domain.dto.request.CancellationRequest;
import com.plantify.payment.domain.dto.request.RefundRequest;
import com.plantify.payment.domain.dto.response.ProcessResponse;
import com.plantify.payment.domain.dto.request.PaymentRequest;

public interface ProcessService {

    ProcessResponse processPayment(PaymentRequest request);
    ProcessResponse processRefund(RefundRequest request);
    ProcessResponse processCancellation(CancellationRequest request);
}
