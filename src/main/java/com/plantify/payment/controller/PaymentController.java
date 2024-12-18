package com.plantify.payment.controller;

import com.plantify.payment.domain.dto.response.ProcessResponse;
import com.plantify.payment.global.response.ApiResponse;
import com.plantify.payment.service.payment.PaymentAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/payments")
public class PaymentController {

    private final PaymentAdminService paymentAdminService;

    // 모든 결제 조회
    @GetMapping
    public ApiResponse<List<ProcessResponse>> getAllPayments() {
        List<ProcessResponse> response = paymentAdminService.getAllPayments();
        return ApiResponse.ok(response);
    }

    // 특정 결제 조회
    @GetMapping("/{paymentId}")
    public ApiResponse<ProcessResponse> getPaymentById(@PathVariable Long paymentId) {
        ProcessResponse response = paymentAdminService.getPaymentById(paymentId);
        return ApiResponse.ok(response);
    }


    // 특정 사용자 결제 조회
    @GetMapping("/user/{userId}")
    public ApiResponse<List<ProcessResponse>> getPaymentsByUserId(@PathVariable Long userId) {
        List<ProcessResponse> response = paymentAdminService.getPaymentsByUserId(userId);
        return ApiResponse.ok(response);
    }
}