package com.example.removie.retry;


import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.io.IOException;


/**
 * 네트워크 또는 IO 작업 중 발생하는 {@link IOException}에 대해 자동 재시도를 수행하는 커스텀 애너테이션입니다.
 *
 * <p>
 * 주로 외부 API 호출, 네트워크 통신 등 일시적인 IO 오류에 대한 복원력을 높이기 위해 사용됩니다.
 * </p>
 *
 */
@Retryable(
        retryFor = {
                IOException.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000, multiplier = 2, maxDelay = 15000)
)
public @interface IORetry {
}
