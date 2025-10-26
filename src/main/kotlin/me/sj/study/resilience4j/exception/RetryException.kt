package me.sj.study.resilience4j.exception

import java.lang.RuntimeException

class RetryException(
    override val message: String?
): RuntimeException() {
}