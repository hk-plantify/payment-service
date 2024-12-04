package com.plantify.payment.controller;

import com.plantify.payment.domain.dto.TransactionRequest;
import com.plantify.payment.domain.dto.TransactionResponse;
import com.plantify.payment.domain.dto.response.PaymentUserResponse;
import com.plantify.payment.global.response.ApiResponse;
import com.plantify.payment.service.PaymentTransactionService;
import com.plantify.payment.service.PaymentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payments")
public class PaymentUserController {

    private final PaymentUserService paymentUserService;
    private final PaymentTransactionService paymentTransactionService;

    // 결제 요청
    @PostMapping("/payments/process")
    public ApiResponse<TransactionResponse> processPayment(@RequestBody TransactionRequest request) {
        TransactionResponse response = paymentTransactionService.processPayment(request);
        return ApiResponse.ok(response);
    }


}