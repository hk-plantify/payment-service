package com.plantify.payment.service.process;

import com.plantify.payment.client.PayServiceClient;
import com.plantify.payment.domain.dto.request.CancellationRequest;
import com.plantify.payment.domain.dto.request.PayBalanceRequest;
import com.plantify.payment.domain.dto.request.RefundRequest;
import com.plantify.payment.domain.dto.response.ProcessResponse;
import com.plantify.payment.domain.dto.request.PaymentRequest;
import com.plantify.payment.domain.entity.Payment;
import com.plantify.payment.domain.entity.Status;
import com.plantify.payment.global.exception.ApplicationException;
import com.plantify.payment.global.exception.errorcode.CancellationErrorCode;
import com.plantify.payment.global.exception.errorcode.PaymentErrorCode;
import com.plantify.payment.global.exception.errorcode.RefundErrorCode;
import com.plantify.payment.global.util.DistributedLock;
import com.plantify.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {

    private final PaymentRepository paymentRepository;
    private final DistributedLock distributedLock;
    private final PayServiceClient payServiceClient;

    @Override
    @Transactional
    public ProcessResponse processPayment(PaymentRequest request) {
        String lockKey = String.format("payment:%d", request.userId());

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Payment payment = paymentRepository.save(request.toEntity());

            try {
                PayBalanceRequest balanceRequest = new PayBalanceRequest(request.userId(), request.amount());
                payServiceClient.checkPayBalance(balanceRequest);
                payment.updateStatus(Status.PAYMENT);
            } catch (ApplicationException ex) {
                if (ex.getHttpStatus() == HttpStatus.BAD_REQUEST) {
                    payment.updateStatus(Status.FAILED);
                } else {
                    throw ex;
                }
            }

            paymentRepository.save(payment);
            return ProcessResponse.from(payment);
        } finally {
            distributedLock.unlock(lockKey);
        }
    }

    @Override
    @Transactional
    public ProcessResponse processCancellation(CancellationRequest request) {
        String lockKey = String.format("payment:%d", request.userId());

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Payment payment = paymentRepository.findById(request.paymentId())
                    .orElseThrow(() -> new ApplicationException(PaymentErrorCode.PAYMENT_NOT_FOUND));

            if (payment.getStatus() != Status.PENDING) {
                throw new ApplicationException(CancellationErrorCode.INVALID_CANCELLATION_STATUS);
            }

            payment.updateReason(request.reason()).updateStatus(Status.CANCELLATION);
            paymentRepository.save(payment);

            return ProcessResponse.from(payment);
        } finally {
            distributedLock.unlock(lockKey);
        }
    }

    @Override
    @Transactional
    public ProcessResponse processRefund(RefundRequest request) {
        String lockKey = String.format("payment:%d", request.userId());

        try {
            distributedLock.tryLockOrThrow(lockKey);

            Payment payment = paymentRepository.findById(request.paymentId())
                    .orElseThrow(() -> new ApplicationException(PaymentErrorCode.PAYMENT_NOT_FOUND));

            if (payment.getStatus() != Status.PAYMENT) {
                throw new ApplicationException(RefundErrorCode.INVALID_REFUND_STATUS);
            }

            payment.updateReason(request.reason()).updateStatus(Status.REFUND);
            paymentRepository.save(payment);

            return ProcessResponse.from(payment);
        } finally {
            distributedLock.unlock(lockKey);
        }
    }
}
