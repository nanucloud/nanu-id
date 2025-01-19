package nanucloud.nanuid.domain.oauth.repository

import nanucloud.nanuid.domain.oauth.domain.OAuthClientAuthCode
import org.springframework.data.repository.CrudRepository
import java.util.*

interface OAuthClientAuthCodeRepository : CrudRepository<OAuthClientAuthCode, UUID> {
    fun findByUserId(userId: String): OAuthClientAuthCode?
}