package me.sj.study.resilience4j

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class Resilience4jApplication

private val logger = LoggerFactory.getLogger(Resilience4jApplication::class.java)

fun main(args: Array<String>) {
    runApplication<Resilience4jApplication>(*args)
}


