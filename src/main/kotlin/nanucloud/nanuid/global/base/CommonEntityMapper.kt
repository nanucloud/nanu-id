package nanucloud.nanuid.global.base

interface CommonEntityMapper<E, D> {
    fun toEntity(domain: D): E

    fun toDomain(domain: E): D
}