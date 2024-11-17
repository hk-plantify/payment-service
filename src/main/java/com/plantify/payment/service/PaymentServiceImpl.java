package com.plantify.payment.service;

import com.plantify.payment.domain.dto.request.PaymentRequest;
import com.plantify.payment.domain.dto.request.PaymentUpdateRequest;
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
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final AuthenticationService authenticationService;

    @Override
    public List<PaymentResponse> getAllPayments() {
        if(authenticationService.validateAdminRole()) {
            return paymentRepository.findAll()
                    .stream()
                    .map(PaymentResponse::from)
                    .collect(Collectors.toList());

        }
        Long userId = authenticationService.getUserId();
        return paymentRepository.findByUserId(userId)
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
    public PaymentResponse updatePaymentStatus(Long paymentId, PaymentUpdateRequest request) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ApplicationException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        authenticationService.validateOwnership(payment.getUserId());

        Payment updatedPayment = payment.toBuilder()
                .status(request.status())
                .build();
        Payment savedPayment = paymentRepository.save(updatedPayment);

        return PaymentResponse.from(savedPayment);
    }

    @Override
    public List<PaymentResponse> getPaymentsByUser(Long userId) {
        authenticationService.validateAdminRole();
        return paymentRepository.findByUserId(userId)
                .stream()
                .map(PaymentResponse::from)
                .collect(Collectors.toList());
    }

}
