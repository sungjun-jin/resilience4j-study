package me.sj.study.resilience4j.exception

import java.lang.RuntimeException

class RecordException(
    override val message: String?
): RuntimeException() {
}