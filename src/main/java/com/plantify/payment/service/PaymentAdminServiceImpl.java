package com.plantify.payment.service;

import com.plantify.payment.domain.dto.request.PaymentAdminRequest;
import com.plantify.payment.domain.dto.response.PaymentAdminResponse;
import com.plantify.payment.domain.entity.Payment;
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
    public List<PaymentAdminResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(PaymentAdminResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentAdminResponse getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ApplicationException(PaymentErrorCode.PAYMENT_NOT_FOUND));
        return PaymentAdminResponse.from(payment);
    }

    @Override
    public PaymentAdminResponse updatePaymentStatus(Long paymentId, PaymentAdminRequest request) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ApplicationException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        payment = payment.toBuilder()
                .status(request.status())
                .failureReason(request.reason())
                .build();
        paymentRepository.save(payment);
        return PaymentAdminResponse.from(payment);
    }

    @Override
    public List<PaymentAdminResponse> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(PaymentAdminResponse::from)
                .collect(Collectors.toList());
    }
}