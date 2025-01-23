package nanucloud.nanuid.global.security.auth

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequiredAuthScope(val requiredScopes: Array<String>)
