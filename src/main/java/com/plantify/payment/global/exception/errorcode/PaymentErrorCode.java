package com.plantify.payment.global.exception.errorcode;

import com.plantify.payment.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {

    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다."),
    PAYMENT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "결제 처리에 실패했습니다."),
    INVALID_PAYMENT_METHOD(HttpStatus.BAD_REQUEST, "유효하지 않은 결제 수단입니다."),
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다."),
    DUPLICATE_PAYMENT(HttpStatus.CONFLICT, "중복된 결제 시도가 감지되었습니다."),
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 결제를 찾을 수 없습니다."),
    PAYMENT_AMOUNT_MISMATCH(HttpStatus.BAD_REQUEST, "결제 금액이 일치하지 않습니다."),
    PAYMENT_CANCELED(HttpStatus.BAD_REQUEST, "이미 취소된 결제입니다."),
    EXPIRED_PAYMENT(HttpStatus.BAD_REQUEST, "결제가 만료되었습니다."),
    INVALID_PAYMENT_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 결제 상태입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

