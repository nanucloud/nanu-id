package nanucloud.nanuid.domain.application.service

import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import nanucloud.nanuid.domain.application.domain.Application
import nanucloud.nanuid.domain.application.entity.ApplicationJpaEntity
import nanucloud.nanuid.domain.application.mapper.ApplicationMapper
import nanucloud.nanuid.domain.application.persistence.repository.ApplicationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class ApplicationInitService(
    private val applicationRepository: ApplicationRepository,
    private val entityManager: EntityManager
) : CommandLineRunner {

    val defaultAppId = UUID.fromString("00000000-0000-0000-0000-000000000001")

    @Transactional
    override fun run(vararg args: String?) {

        if (applicationRepository.findByName("NANU ID DASHBOARD").isEmpty) {
            entityManager.createNativeQuery(
                """
                INSERT INTO tbl_application (
                    id, owner_id, name, description, 
                    is_public, application_secret, version
                ) VALUES (
                    :id, :ownerId, :name, :description,
                    :isPublic, :applicationSecret, 0
                )
            """
            )
                .setParameter("id", defaultAppId)
                .setParameter("ownerId", UUID.randomUUID())
                .setParameter("name", "NANU ID DASHBOARD")
                .setParameter("description", "default application")
                .setParameter("isPublic", true)
                .setParameter("applicationSecret", UUID.randomUUID().toString())
                .executeUpdate()

            entityManager.flush()
            entityManager.clear()
        }

    }
}