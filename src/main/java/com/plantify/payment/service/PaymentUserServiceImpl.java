package com.plantify.payment.service;

import com.plantify.payment.config.RedisLock;
import com.plantify.payment.domain.dto.TransactionRequest;
import com.plantify.payment.domain.dto.TransactionResponse;
import com.plantify.payment.domain.entity.Status;
import com.plantify.payment.util.UserInfoProvider;
import com.plantify.payment.domain.dto.response.PaymentUserResponse;
import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.global.exception.ApplicationException;
import com.plantify.payment.global.exception.errorcode.PaymentErrorCode;
import com.plantify.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentUserServiceImpl implements PaymentUserService, PaymentTransactionService {

    private final PaymentRepository paymentRepository;
    private final RedisLock redisLock;

    private static final int LOCK_TIMEOUT_MS = 3000;

    @Override
    @Transactional
    public TransactionResponse processPayment(TransactionRequest request) {
        String lockKey = String.format("payment:%d", request.userId());

        try {
            if (!redisLock.tryLock(lockKey, LOCK_TIMEOUT_MS)) {
                throw new ApplicationException(PaymentErrorCode.CONCURRENT_UPDATE);
            }

            Payment payment = paymentRepository.save(request.toEntity());

            payment = payment.toBuilder()
                    .status(Status.SUCCESS)
                    .failureReason(null)
                    .build();
            paymentRepository.save(payment);

            return TransactionResponse.from(payment);
        } finally {
            redisLock.unlock(lockKey);
        }
    }
}
