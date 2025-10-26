package me.sj.study.resilience4j.retry

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value = ["/"])
class RetryController(private val retryService: RetryService) {

    @GetMapping("/retry-call")
    fun apiCall(@RequestParam param: String?): String {
        return retryService.process(param)
    }
}