package nanucloud.nanuid.domain.application.service

import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import nanucloud.nanuid.domain.application.persistence.repository.ApplicationJpaRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class ApplicationInitService(
    private val applicationJpaRepository: ApplicationJpaRepository,
    private val entityManager: EntityManager
) : CommandLineRunner {

    val defaultAppId = UUID.fromString("00000000-0000-0000-0000-000000000001")

    @Transactional
    override fun run(vararg args: String?) {

        if (applicationJpaRepository.findByName("NANU ID DASHBOARD").isEmpty) {
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
                .setParameter("ownerId", UUID.randomUUID().toString())
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