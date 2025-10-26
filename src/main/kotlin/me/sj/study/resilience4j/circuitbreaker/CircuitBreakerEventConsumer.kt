package me.sj.study.resilience4j.circuitbreaker

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.core.registry.EntryAddedEvent
import io.github.resilience4j.core.registry.EntryRemovedEvent
import io.github.resilience4j.core.registry.EntryReplacedEvent
import io.github.resilience4j.core.registry.RegistryEventConsumer
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CircuitBreakerEventConsumer {
    private val logger = LoggerFactory.getLogger(CircuitBreakerEventConsumer::class.java)

    @Bean
    fun customCircuitBreakerRegistryEventConsumer(): RegistryEventConsumer<CircuitBreaker?> {
        return object : RegistryEventConsumer<CircuitBreaker?> {
            override fun onEntryAddedEvent(entryAddedEvent: EntryAddedEvent<CircuitBreaker?>) {
                logger.info("CircuitBreaker RegistryEventConsumer.onEntryAddedEvent")
                entryAddedEvent.getAddedEntry().eventPublisher.onEvent({ event -> logger.info(event.toString()) })
                entryAddedEvent.getAddedEntry().eventPublisher
                    .onFailureRateExceeded({ event -> logger.info("CircuitBreaker failure rate exceeded: {}", event.eventType) })
            }

            override fun onEntryRemovedEvent(entryRemoveEvent: EntryRemovedEvent<CircuitBreaker?>) {
                logger.info("RegistryEventConsumer.onEntryRemovedEvent")
            }

            override fun onEntryReplacedEvent(entryReplacedEvent: EntryReplacedEvent<CircuitBreaker?>) {
                logger.info("RegistryEventConsumer.onEntryReplacedEvent")
            }
        }
    }
}