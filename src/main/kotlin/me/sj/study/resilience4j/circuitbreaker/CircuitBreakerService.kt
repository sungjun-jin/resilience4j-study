package me.sj.study.resilience4j.circuitbreaker

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import me.sj.study.resilience4j.exception.IgnoreException
import me.sj.study.resilience4j.exception.RecordException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CircuitBreakerService {
    private val logger = LoggerFactory.getLogger(javaClass)

    @CircuitBreaker(name = "simpleCircuitBreakerConfig", fallbackMethod = "fallback")
    fun process(param: String?): String? {
        return callAnotherServer(param)
    }

    /**
     * circuit breaker가 열렸거나 실패할 때 실행되는 메소드
     */
    private fun fallback(param: String?, ex: Exception): String {
        // fallback 처리
        logger.info("Circuit breaker fallback triggered for request: $param, reason: ${ex.message}")
        return "fallback 기능 실행 (요청: $param)"
    }

    /**
     * 실제 다른 서버를 호출하는 메소드
     */
    private fun callAnotherServer(param: String?): String? {
        when (param) {
            "failure" -> throw RecordException("record exception")
            "ignore" -> throw IgnoreException("ignore exception")
            "slow" // 3초 이상 걸리는 경우도 실패로 간주
                -> Thread.sleep(4000)
        };


        return param
    }
}