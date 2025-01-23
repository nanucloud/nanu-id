package nanucloud.nanuid.global.security.auth

import jakarta.servlet.http.HttpServletRequest
import nanucloud.nanuid.domain.auth.domain.AuthScope
import nanucloud.nanuid.global.security.auth.exception.NoPermissionException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Aspect
@Component
class AuthScopeAspect {
    @Autowired lateinit var request: HttpServletRequest;

    @Around("@annotation(requiredAuthScope)")
    fun checkAuthScopePermission(joinPoint: ProceedingJoinPoint, requiredAuthScope: RequiredAuthScope): Any {
        val authScopes = request.getAttribute("authScopes") as? Set<AuthScope>

        if (authScopes.isNullOrEmpty()) {
            throw NoPermissionException
        }

        val requiredScopes = requiredAuthScope.requiredScopes.toSet()
        val userScopes = authScopes.map { it.name }.toSet()

        if (!userScopes.containsAll(requiredScopes)) {
            throw NoPermissionException
        }

        return joinPoint.proceed() // 원래 메서드를 실행
    }
}