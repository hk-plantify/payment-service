package com.plantify.payment.service.payment;

import com.plantify.payment.domain.dto.response.ProcessResponse;
import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;
import com.plantify.payment.global.exception.ApplicationException;
import com.plantify.payment.global.exception.errorcode.PaymentErrorCode;
import com.plantify.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentAdminServiceImpl implements PaymentAdminService {

    private final PaymentRepository paymentRepository;

    @Override
    public List<ProcessResponse> getAllPayments() {
        return paymentRepository.findByStatus(Status.SUCCESS)
                .stream()
                .map(ProcessResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public ProcessResponse getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ApplicationException(PaymentErrorCode.PAYMENT_NOT_FOUND));
        return ProcessResponse.from(payment);
    }

    @Override
    public List<ProcessResponse> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserIdAndStatus(userId, Status.SUCCESS)
                .stream()
                .map(ProcessResponse::from)
                .toList();
    }
}