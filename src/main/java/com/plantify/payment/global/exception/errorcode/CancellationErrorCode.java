package com.plantify.payment.global.exception.errorcode;

import com.plantify.payment.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CancellationErrorCode implements ErrorCode {

    CANCELLATION_NOT_FOUND(HttpStatus.NOT_FOUND, "취소 요청을 찾을 수 없습니다."),
    INVALID_CANCELLATION_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 취소 상태입니다."),
    CANCELLATION_ALREADY_PROCESSED(HttpStatus.BAD_REQUEST, "이미 처리된 취소 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "취소 처리 중 서버 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}

