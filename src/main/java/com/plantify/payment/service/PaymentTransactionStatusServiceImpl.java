package com.plantify.payment.service;

import com.plantify.payment.config.RedisLock;
import com.plantify.payment.domain.dto.TransactionStatusMessage;
import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;
import com.plantify.payment.global.exception.ApplicationException;
import com.plantify.payment.global.exception.errorcode.PaymentErrorCode;
import com.plantify.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentTransactionStatusServiceImpl implements PaymentTransactionStatusService {

    private final PaymentRepository paymentRepository;
    private final RedisLock redisLock;

    private static final int LOCK_TIMEOUT_MS = 3000;

    @Override
    public void processSuccessfulTransaction(TransactionStatusMessage message) {
        String lockKey = String.format("transaction:%d", message.userId());

        try {
            if(!redisLock.tryLock(lockKey, LOCK_TIMEOUT_MS)) {
                throw new ApplicationException(PaymentErrorCode.CONCURRENT_UPDATE);
            }

            Payment payment = paymentRepository.findById(message.transactionId())
                    .orElseThrow(() -> new ApplicationException(PaymentErrorCode.PAYMENT_NOT_FOUND));

            payment = payment.toBuilder()
                    .status(Status.SUCCESS)
                    .failureReason(null)
                    .build();
            paymentRepository.save(payment);
        } finally {
            redisLock.unlock(lockKey);
        }
    }

    @Override
    public void processFailedTransaction(TransactionStatusMessage message) {
        String lockKey = String.format("transaction:%d", message.userId());

        try {
            if (!redisLock.tryLock(lockKey, LOCK_TIMEOUT_MS)) {
                throw new ApplicationException(PaymentErrorCode.CONCURRENT_UPDATE);
            }

            Payment payment = paymentRepository.findById(message.transactionId())
                    .orElseThrow(() -> new ApplicationException(PaymentErrorCode.PAYMENT_NOT_FOUND));

            payment = payment.toBuilder()
                    .status(Status.FAILED)
                    .failureReason("Transaction failed")
                    .build();
            paymentRepository.save(payment);
        } finally {
            redisLock.unlock(lockKey);
        }
    }

}
