package me.sj.study.resilience4j.exception

import java.lang.RuntimeException

class IgnoreException(
    override val message: String?
): RuntimeException() {
}