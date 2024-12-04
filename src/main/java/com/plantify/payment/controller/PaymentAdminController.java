package com.plantify.payment.controller;

import com.plantify.payment.domain.dto.request.PaymentAdminRequest;
import com.plantify.payment.domain.dto.response.PaymentAdminResponse;
import com.plantify.payment.global.response.ApiResponse;
import com.plantify.payment.service.PaymentAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/payments")
public class PaymentAdminController {

    private final PaymentAdminService paymentAdminService;

    // 모든 결제 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentAdminResponse>>> getAllPayments() {
        List<PaymentAdminResponse> response = paymentAdminService.getAllPayments();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 특정 결제 조회
    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentAdminResponse>> getPaymentById(@PathVariable Long paymentId) {
        PaymentAdminResponse response = paymentAdminService.getPaymentById(paymentId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 특정 결제 상태 변경 (승인/거절)
    @PutMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentAdminResponse>> updatePaymentStatus(
            @PathVariable Long paymentId, @RequestBody PaymentAdminRequest request) {
        PaymentAdminResponse response = paymentAdminService.updatePaymentStatus(paymentId, request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 특정 사용자 결제 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<PaymentAdminResponse>>> getPaymentsByUserId(@PathVariable Long userId) {
        List<PaymentAdminResponse> response = paymentAdminService.getPaymentsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}