package com.plantify.payment.service;

import com.plantify.payment.domain.dto.TransactionRequest;
import com.plantify.payment.domain.dto.TransactionResponse;

public interface PaymentTransactionService {

    TransactionResponse processPayment(TransactionRequest request);
}
