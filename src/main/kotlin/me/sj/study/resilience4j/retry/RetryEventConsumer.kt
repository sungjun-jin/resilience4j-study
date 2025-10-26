package me.sj.study.resilience4j.retry

import io.github.resilience4j.core.registry.EntryAddedEvent
import io.github.resilience4j.core.registry.EntryRemovedEvent
import io.github.resilience4j.core.registry.EntryReplacedEvent
import io.github.resilience4j.core.registry.RegistryEventConsumer
import io.github.resilience4j.retry.Retry
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RetryEventConsumer {
    private val logger = LoggerFactory.getLogger(RetryEventConsumer::class.java)

    @Bean
    fun customRetryRegistryEventConsumer(): RegistryEventConsumer<Retry?> {
        return object : RegistryEventConsumer<Retry?> {
            override fun onEntryAddedEvent(entryAddedEvent: EntryAddedEvent<Retry?>) {
                logger.info("RegistryEventConsumer.onEntryAddedEvent")
                entryAddedEvent.getAddedEntry().eventPublisher.onEvent({ event -> logger.info(event.toString()) })
            }

            override fun onEntryRemovedEvent(entryRemoveEvent: EntryRemovedEvent<Retry?>) {
                logger.info("RegistryEventConsumer.onEntryRemovedEvent")
            }

            override fun onEntryReplacedEvent(entryReplacedEvent: EntryReplacedEvent<Retry?>) {
                logger.info("RegistryEventConsumer.onEntryReplacedEvent")
            }
        }
    }
}