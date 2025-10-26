package me.sj.study.resilience4j.circuitbreaker

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import me.sj.study.resilience4j.exception.IgnoreException
import me.sj.study.resilience4j.exception.RecordException
import me.sj.study.resilience4j.exception.RetryException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CurcuitBreakerService {
    private val logger = LoggerFactory.getLogger(javaClass)

    @CircuitBreaker(name = "simpleCircuitBreakerConfig", fallbackMethod = "fallback")
    fun process(param: String?): String? {
        return callAnotherServer(param)
    }

    /**
     * retry에 전부 실패하면 실행되는 메소드
     */
    private fun fallback(param: String?, ex: Exception): String {
        // fallback 처리
        logger.info("fallback! your request is $param");
        return "Recovered: $ex"
    }

    /**
     * 실제 다른 서버를 호출하는 메소드
     */
    private fun callAnotherServer(param: String?): String? {
        when (param) {
            "a" -> throw RecordException("record exception")
            "b" -> throw IgnoreException("ignore exception")
            "c" // 3초 이상 걸리는 경우도 실패로 간주
                -> Thread.sleep(4000)
        };


        return param
    }
}