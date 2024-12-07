package com.plantify.payment.service;

import com.plantify.payment.domain.dto.TransactionStatusMessage;
import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;
import com.plantify.payment.global.exception.ApplicationException;
import com.plantify.payment.global.exception.errorcode.PaymentErrorCode;
import com.plantify.payment.global.util.DistributedLock;
import com.plantify.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentTransactionStatusServiceImpl implements PaymentTransactionStatusService {

    private final PaymentRepository paymentRepository;
    private final DistributedLock distributedLock;

    @Override
    public void processSuccessfulTransaction(TransactionStatusMessage message) {
        String lockKey = String.format("transaction:%d", message.userId());

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Payment payment = paymentRepository.findById(message.transactionId())
                    .orElseThrow(() -> new ApplicationException(PaymentErrorCode.PAYMENT_NOT_FOUND));

            payment.updateStatus(Status.SUCCESS);
        } finally {
            distributedLock.unlock(lockKey);
        }
    }

    @Override
    public void processFailedTransaction(TransactionStatusMessage message) {
        String lockKey = String.format("transaction:%d", message.userId());

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Payment payment = paymentRepository.findById(message.transactionId())
                    .orElseThrow(() -> new ApplicationException(PaymentErrorCode.PAYMENT_NOT_FOUND));

            payment.updateStatus(Status.FAILED);
        } finally {
            distributedLock.unlock(lockKey);
        }
    }
}
