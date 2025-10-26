# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot study project demonstrating Resilience4j retry patterns in Kotlin. The application showcases how to implement retry logic with custom exception handling using Spring Cloud Circuit Breaker.

## Build Commands

**Build the project:**
```bash
./gradlew build
```

**Run the application:**
```bash
./gradlew bootRun
```

**Run tests:**
```bash
./gradlew test
```

**Run a single test class:**
```bash
./gradlew test --tests "me.sj.study.resilience4j.Resilience4jApplicationTests"
```

**Clean build:**
```bash
./gradlew clean build
```

## Architecture

### Retry Pattern Implementation

The project demonstrates Resilience4j retry functionality with the following architecture:

- **Configuration**: Retry behavior is configured in `src/main/resources/application.yml` with instance-based configuration
  - `simpleRetryConfig` instance uses default config with 3 max attempts, 1000ms wait duration
  - Exceptions are categorized as either retry-triggering (`RetryException`) or ignored (`IgnoreException`)

- **Service Layer**: `RetryService` applies the `@Retry` annotation with fallback method
  - Retry logic is declarative via annotation: `@Retry(name = "simpleRetryConfig", fallbackMethod = "fallback")`
  - Fallback method executes only after all retry attempts are exhausted

- **Exception Handling**: Two custom exception types control retry behavior
  - `RetryException`: Triggers retry attempts as configured in `retryExceptions`
  - `IgnoreException`: Bypasses retry logic and directly propagates to client as configured in `ignoreExceptions`

- **Event Monitoring**: `Resilience4jApplication` registers a `RegistryEventConsumer<Retry>` bean that logs all retry events
  - Monitors when retry instances are added, removed, or replaced
  - Subscribes to retry events for observability

### Package Structure

```
me.sj.study.resilience4j/
├── Resilience4jApplication.kt    # Main app with retry event consumer bean
├── retry/
│   ├── RetryController.kt        # REST endpoint exposing retry functionality
│   └── RetryService.kt           # Service with @Retry annotation and fallback
└── exception/
    ├── RetryException.kt         # Triggers retry attempts
    └── IgnoreException.kt        # Skips retry logic
```

## Technology Stack

- Kotlin 1.9.25
- Spring Boot 3.5.7
- Spring Cloud 2025.0.0
- Resilience4j (via spring-cloud-starter-circuitbreaker-resilience4j)
- Java 21 toolchain
- Gradle with Kotlin DSL

## Testing the Retry Functionality

The `/api-call` endpoint accepts a `param` query parameter and demonstrates retry behavior:
- Throws `RetryException` by default, triggering retries
- Can be modified to throw `IgnoreException` to bypass retry logic
- Check logs to observe retry attempts and event publishing
