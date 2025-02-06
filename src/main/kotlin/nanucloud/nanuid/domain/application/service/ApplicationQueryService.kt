package nanucloud.nanuid.domain.application.service

import jakarta.transaction.Transactional
import nanucloud.nanuid.domain.application.domain.Application
import nanucloud.nanuid.domain.application.mapper.ApplicationMapper
import nanucloud.nanuid.domain.application.persistence.repository.ApplicationRepository
import nanucloud.nanuid.domain.user.facade.UserFacade
import org.springframework.stereotype.Service

@Service
class ApplicationQueryService(
    private val applicationRepository: ApplicationRepository,
    private val userFacade: UserFacade,
    private val applicationMapper: ApplicationMapper
) {
    @Transactional
    fun execute(): List<Application> {
        val currentUser = userFacade.getCurrentUser ()
        val entities = applicationRepository.findAllByOwnerId(currentUser.userId.toString())
        return entities.map { applicationMapper.toDomain(it) }
    }
}