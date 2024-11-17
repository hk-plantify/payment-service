package com.plantify.payment.controller;

import com.plantify.payment.domain.dto.request.PaymentRequest;
import com.plantify.payment.domain.dto.response.PaymentResponse;
import com.plantify.payment.domain.entity.Status;
import com.plantify.payment.global.response.ApiResponse;
import com.plantify.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {
        List<PaymentResponse> allPayments = paymentService.getAllPayments();
        return ResponseEntity.ok(ApiResponse.ok(allPayments));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(
            @RequestBody PaymentRequest request) {
        PaymentResponse payment = paymentService.createPayment(request);
        return ResponseEntity.ok(ApiResponse.ok(payment));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPayment(@PathVariable Long paymentId) {
        PaymentResponse payment = paymentService.getPayment(paymentId);
        return ResponseEntity.ok(ApiResponse.ok(payment));
    }

    @PostMapping("/{paymentId}/cancel")
    public ResponseEntity<ApiResponse<PaymentResponse>> cancelPayment(
            @PathVariable Long paymentId) {
        PaymentResponse response = paymentService.cancelPayment(paymentId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PutMapping("/{paymentId}/status")
    public ResponseEntity<ApiResponse<PaymentResponse>> updatePayment(
            @PathVariable Long paymentId, @RequestParam Status status) {
        PaymentResponse response = paymentService.updatePayment(paymentId, status);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByUser() {
        List<PaymentResponse> payments = paymentService.getPaymentsByUser();
        return ResponseEntity.ok(ApiResponse.ok(payments));
    }
}
