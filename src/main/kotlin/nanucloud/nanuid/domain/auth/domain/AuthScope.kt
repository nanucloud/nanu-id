package nanucloud.nanuid.domain.auth.domain

enum class AuthScope(val bit: Int) {
    FULL_ACCESS(1 shl 0),
    NAME_ACCESS(1 shl 1),
    EMAIL_ACCESS(1 shl 2),
    USERID_ACCESS(1 shl 3),
    BIRTH_ACCESS(1 shl 4)
}