package me.sj.study.resilience4j.retry

import io.github.resilience4j.retry.annotation.Retry
import me.sj.study.resilience4j.exception.RetryException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RetryService {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Retry(name = "simpleRetryConfig", fallbackMethod = "fallback")
    fun process(param: String?): String {
        return callAnotherServer(param)
    }

    /**
     * retry에 전부 실패하면 실행되는 메소드
     */
    private fun fallback(param: String?, ex: Exception): String {
        // fallback 처리
        return "Recovered: $ex"
    }

    /**
     * 실제 다른 서버를 호출하는 메소드
     */
    private fun callAnotherServer(param: String?): String {
        // 다른 서버 호출 (생략)

        throw RetryException("retry exception")
    }
}