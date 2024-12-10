package com.plantify.payment.client;

import com.plantify.payment.domain.dto.response.PayBalanceResponse;
import com.plantify.payment.global.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pay-service", url = "${pay.service.url}")
public interface PayServiceClient {

    @GetMapping("/v1/pay/check")
    ApiResponse<PayBalanceResponse> checkPayBalance(@RequestParam Long amount);
}
