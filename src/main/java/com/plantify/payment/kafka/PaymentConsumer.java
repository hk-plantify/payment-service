package com.plantify.payment.kafka;

import com.plantify.payment.domain.dto.TransactionStatusMessage;
import com.plantify.payment.service.PaymentTransactionStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final PaymentTransactionStatusService paymentTransactionStatusService;

    private static final String SUCCESS_STATUS = "SUCCESS";
    private static final String FAILED_STATUS = "FAILED";

    @KafkaListener(
            topics = "${spring.kafka.topic.transaction-status}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleTransactionStatus(TransactionStatusMessage message) {
        try {
            switch (message.status()) {
                case SUCCESS_STATUS -> paymentTransactionStatusService.processSuccessfulTransaction(message);
                case FAILED_STATUS -> paymentTransactionStatusService.processFailedTransaction(message);
                default -> log.warn("Unknown status: {}", message.status());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}