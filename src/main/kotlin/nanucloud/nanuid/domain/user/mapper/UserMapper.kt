package nanucloud.nanuid.domain.user.mapper

import nanucloud.nanuid.domain.user.domain.User
import nanucloud.nanuid.domain.user.entity.UserJpaEntity
import nanucloud.nanuid.global.base.CommonEntityMapper
import org.springframework.stereotype.Component

@Component
class UserMapper: CommonEntityMapper<UserJpaEntity, User> {

    override fun toEntity(domain: User): UserJpaEntity {
        return UserJpaEntity(
            id = domain.userId,
            deviceToken = domain.deviceToken,
            pin = domain.pin,
            password = domain.password,
            name = domain.name,
            email = domain.email,
            birthDate = domain.birthDate,
            isEnabled = domain.isEnabled,
            isAccountLocked = domain.isAccountLocked
        )
    }

    override fun toDomain(entity: UserJpaEntity): User {
        return User(
            userId = entity.id,
            deviceToken = entity.deviceToken,
            pin = entity.pin,
            password = entity.password,
            name = entity.name,
            email = entity.email,
            birthDate = entity.birthDate,
            isEnabled = entity.isEnabled,
            isAccountLocked = entity.isAccountLocked
        )
    }
}