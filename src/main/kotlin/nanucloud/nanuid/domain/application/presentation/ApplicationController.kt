package nanucloud.nanuid.domain.application.presentation

import nanucloud.nanuid.domain.application.domain.Application
import nanucloud.nanuid.domain.application.presentation.dto.request.ApplicationCreateRequest
import nanucloud.nanuid.domain.application.service.ApplicationCreateService
import nanucloud.nanuid.domain.application.service.ApplicationQueryService
import nanucloud.nanuid.global.security.auth.RequiredAuthScope
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/application")
class ApplicationController(
    private val applicationCreateService: ApplicationCreateService,
    private val applicationQueryService: ApplicationQueryService
) {
    @RequiredAuthScope(["FULL_ACCESS"])
    @PostMapping("/create")
    fun createApplication(@RequestBody applicationCreateRequest: ApplicationCreateRequest): Application {
        return applicationCreateService.execute(applicationCreateRequest)
    }

    @RequiredAuthScope(["FULL_ACCESS"])
    @GetMapping("/list")
    fun createApplication(): List<Application> {
        return applicationQueryService.execute()
    }
}