package com.plantify.payment.service.refund;

import com.plantify.payment.domain.dto.response.ProcessResponse;

import java.util.List;

public interface RefundService {

    List<ProcessResponse> getAllRefunds();
    ProcessResponse getRefundById(Long paymentId);
    List<ProcessResponse> getRefundsByUserId(Long userId);
}
