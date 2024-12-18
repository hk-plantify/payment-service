package com.plantify.payment.global.util;

import com.plantify.payment.config.RedisLock;
import com.plantify.payment.global.exception.ApplicationException;
import com.plantify.payment.global.exception.errorcode.PaymentErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DistributedLock {

    private final RedisLock redisLock;

    private static final int LOCK_TIMEOUT_MS = 3000;

    public void tryLockOrThrow(String lockKey) {
        log.info("{}", lockKey);
        if (!redisLock.tryLock(lockKey, LOCK_TIMEOUT_MS)) {
            throw new ApplicationException(PaymentErrorCode.CONCURRENT_UPDATE);
        }
    }

    public void unlock(String lockKey) {
        redisLock.unlock(lockKey);
    }
}