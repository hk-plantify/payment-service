package com.plantify.payment.client;

import com.plantify.payment.domain.dto.request.PayBalanceRequest;
import com.plantify.payment.domain.dto.response.PayBalanceResponse;
import com.plantify.payment.global.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pay-service", url = "${pay.service.url}")
public interface PayServiceClient {

    @PostMapping("/v1/pay/check")
    ApiResponse<PayBalanceResponse> checkPayBalance(@RequestBody PayBalanceRequest request);
}
