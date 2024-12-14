package com.plantify.payment.service.cancellation;

import com.plantify.payment.domain.dto.response.ProcessResponse;

import java.util.List;

public interface CancellationService {

    List<ProcessResponse> getAllCancellations();
    ProcessResponse getCancellationById(Long cancellationId);
    List<ProcessResponse> getCancellationsByUserId(Long userId);
}
