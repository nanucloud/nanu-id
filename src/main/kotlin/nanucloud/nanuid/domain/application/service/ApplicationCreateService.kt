package nanucloud.nanuid.domain.application.service

import jakarta.transaction.Transactional
import nanucloud.nanuid.domain.application.domain.Application
import nanucloud.nanuid.domain.application.mapper.ApplicationMapper
import nanucloud.nanuid.domain.application.persistence.repository.ApplicationJpaRepository
import nanucloud.nanuid.domain.application.presentation.dto.request.ApplicationCreateRequest
import nanucloud.nanuid.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import java.util.*

@Service
class ApplicationCreateService(
    private val applicationJpaRepository: ApplicationJpaRepository,
    private val applicationMapper: ApplicationMapper,
    private val userFacade: UserFacade
) {
    @Transactional
    fun execute(applicationCreateRequest: ApplicationCreateRequest): Application {
        val currentUser = userFacade.getCurrentUser()
        print(currentUser.userId)
        val application = Application(
            ownerId = currentUser.userId!!,
            name = applicationCreateRequest.name,
            description = applicationCreateRequest.description,
            isPublic = applicationCreateRequest.isPublic,
            applicationSecret = generateApplicationSecret()
        )

        val entity = applicationMapper.toEntity(application)
        applicationJpaRepository.save(entity)

        return application
    }

    private fun generateApplicationSecret(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }
}