package com.plantify.payment.service;

import com.plantify.payment.domain.dto.response.PaymentResponse;
import com.plantify.payment.domain.dto.request.PaymentRequest;
import com.plantify.payment.domain.entity.Status;
import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.global.util.DistributedLock;
import com.plantify.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    private final PaymentRepository paymentRepository;
    private final DistributedLock distributedLock;


    @Override
    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {
        String lockKey = String.format("payment:%d", request.userId());

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Payment payment = paymentRepository.save(request.toEntity());
            payment.updateStatus(Status.SUCCESS);

            return PaymentResponse.from(payment);
        } finally {
            distributedLock.unlock(lockKey);
        }
    }
}
