package com.plantify.payment.controller;

import com.plantify.payment.domain.dto.response.PaymentResponse;
import com.plantify.payment.domain.dto.request.PaymentAdminRequest;
import com.plantify.payment.domain.dto.request.PaymentRequest;
import com.plantify.payment.domain.dto.response.PaymentAdminResponse;
import com.plantify.payment.global.response.ApiResponse;
import com.plantify.payment.service.PaymentAdminService;
import com.plantify.payment.service.PaymentTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentTransactionService paymentTransactionService;
    private final PaymentAdminService paymentAdminService;

    // 결제 요청
    @PostMapping("/v1/payments/process")
    public PaymentResponse processPayment(@RequestBody PaymentRequest request) {
        PaymentResponse response = paymentTransactionService.processPayment(request);
        return response;
    }

    // 모든 결제 조회
    @GetMapping("/v1/admin/payments")
    public ApiResponse<List<PaymentAdminResponse>> getAllPayments() {
        List<PaymentAdminResponse> response = paymentAdminService.getAllPayments();
        return ApiResponse.ok(response);
    }

    // 특정 결제 조회
    @GetMapping("/v1/admin/payments/{paymentId}")
    public ApiResponse<PaymentAdminResponse> getPaymentById(@PathVariable Long paymentId) {
        PaymentAdminResponse response = paymentAdminService.getPaymentById(paymentId);
        return ApiResponse.ok(response);
    }

    // 특정 결제 상태 변경 (승인/거절)
    @PutMapping("/v1/admin/payments/{paymentId}")
    public ApiResponse<PaymentAdminResponse> updatePaymentStatus(
            @PathVariable Long paymentId, @RequestBody PaymentAdminRequest request) {
        PaymentAdminResponse response = paymentAdminService.updatePaymentStatus(paymentId, request);
        return ApiResponse.ok(response);
    }

    // 특정 사용자 결제 조회
    @GetMapping("/v1/admin/payments/user/{userId}")
    public ApiResponse<List<PaymentAdminResponse>> getPaymentsByUserId(@PathVariable Long userId) {
        List<PaymentAdminResponse> response = paymentAdminService.getPaymentsByUserId(userId);
        return ApiResponse.ok(response);
    }
}