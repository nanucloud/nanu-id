package nanucloud.nanuid.global.security.base

interface CommonEntityMapper<E, D> {
    fun toEntity(domain: D): E

    fun toDomain(domain: E): D
}