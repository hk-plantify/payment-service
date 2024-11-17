package com.plantify.payment.service;

import com.plantify.payment.domain.dto.request.PaymentRequest;
import com.plantify.payment.domain.dto.response.PaymentResponse;
import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;
import com.plantify.payment.global.exception.ApplicationException;
import com.plantify.payment.global.exception.errorcode.PaymentErrorCode;
import com.plantify.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService, InternalService {

    private final PaymentRepository paymentRepository;
    private final AuthenticationService authenticationService;

    @Override
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(PaymentResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        Long userId = authenticationService.getUserId();
        Payment payment = request.toEntity(userId);
        Payment savedPayment = paymentRepository.save(payment);

        return PaymentResponse.from(savedPayment);
    }

    @Override
    public PaymentResponse getPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ApplicationException(PaymentErrorCode.PAYMENT_NOT_FOUND));
        authenticationService.validateOwnership(payment.getUserId());

        return PaymentResponse.from(payment);
    }

    @Override
    public PaymentResponse cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ApplicationException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        if (payment.getStatus() != Status.PENDING) {
            throw new ApplicationException(PaymentErrorCode.PAYMENT_CANCELED);
        }
        authenticationService.validateOwnership(payment.getUserId());

        Payment updatedPayment = payment.toBuilder()
                .status(Status.CANCELLED)
                .build();
        Payment savedPayment = paymentRepository.save(updatedPayment);

        return PaymentResponse.from(savedPayment);
    }

    @Override
    public PaymentResponse updatePayment(Long paymentId, Status status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ApplicationException(PaymentErrorCode.PAYMENT_NOT_FOUND));
        validateStatusTransition(status, payment);

        authenticationService.validateOwnership(payment.getUserId());

        Payment updatedPayment = payment.toBuilder()
                .status(status)
                .build();
        Payment savedPayment = paymentRepository.save(updatedPayment);

        return PaymentResponse.from(savedPayment);
    }

    @Override
    public List<PaymentResponse> getPaymentsByUser() {
        Long userId = authenticationService.getUserId();
        return paymentRepository.findByUserId(userId)
                .stream()
                .map(PaymentResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public Status validateStatusTransition(Status status, Payment payment) {
        Status currentStatus = payment.getStatus();

        if (currentStatus == Status.PENDING && !(status == Status.PROCESSING || status == Status.CANCELLED || status == Status.FAILED)) {
            throw new ApplicationException(PaymentErrorCode.INVALID_PAYMENT_STATUS);
        }
        if (currentStatus == Status.PROCESSING && !(status == Status.COMPLETED || status == Status.FAILED)) {
            throw new ApplicationException(PaymentErrorCode.INVALID_PAYMENT_STATUS);
        }
        if (currentStatus == Status.COMPLETED) {
            throw new ApplicationException(PaymentErrorCode.PAYMENT_CANCELED);
        }
        return currentStatus;
    }

}
