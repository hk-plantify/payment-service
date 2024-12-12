package com.plantify.payment.service;

import com.plantify.payment.client.PayServiceClient;
import com.plantify.payment.domain.dto.request.PayBalanceRequest;
import com.plantify.payment.domain.dto.response.PaymentResponse;
import com.plantify.payment.domain.dto.request.PaymentRequest;
import com.plantify.payment.domain.entity.Status;
import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.global.exception.ApplicationException;
import com.plantify.payment.global.exception.errorcode.PaymentErrorCode;
import com.plantify.payment.global.response.ApiResponse;
import com.plantify.payment.global.util.DistributedLock;
import com.plantify.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    private final PaymentRepository paymentRepository;
    private final DistributedLock distributedLock;
    private final PayServiceClient payServiceClient;

    @Override
    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {
        String lockKey = String.format("payment:%d", request.userId());

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Payment payment = paymentRepository.save(request.toEntity());

            try {
                PayBalanceRequest balanceRequest = new PayBalanceRequest(request.userId(), request.amount());
                payServiceClient.checkPayBalance(balanceRequest);
                payment.updateStatus(Status.SUCCESS);
            } catch (ApplicationException ex) {
                if (ex.getHttpStatus() == HttpStatus.BAD_REQUEST) {
                    payment.updateStatus(Status.FAILED);
                } else {
                    throw ex;
                }
            }

            return PaymentResponse.from(payment);
        } finally {
            distributedLock.unlock(lockKey);
        }
    }
}
