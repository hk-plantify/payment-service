package com.plantify.payment.service;

import com.plantify.payment.domain.dto.TransactionStatusMessage;

public interface PaymentTransactionStatusService {

    void processSuccessfulTransaction(TransactionStatusMessage message);
    void processFailedTransaction(TransactionStatusMessage message);
}
