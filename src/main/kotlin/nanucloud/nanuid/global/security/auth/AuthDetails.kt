package org.example.pmanchu.global.security.auth

import nanucloud.nanuid.domain.user.domain.User
import nanucloud.nanuid.domain.user.entity.UserJpaEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthDetails(private val user: User) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(GrantedAuthority { "USER" })
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.name
    }

    fun getUserId(): String {
        return user.userId.toString()
    }

    fun getUser(): User {
        return user
    }

    override fun isAccountNonExpired(): Boolean {
        return false
    }

    override fun isAccountNonLocked(): Boolean {
        return !user.isAccountLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return user.isEnabled
    }
}
