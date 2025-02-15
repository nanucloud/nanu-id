package nanucloud.nanuid.global.error.exception

enum class ErrorCode (
    val httpStatus: Int,
    val message : String
) {
    NOT_FOUND(404,"Not found"),
    INVALID_TOKEN(401,"Invalid token"),
    EXPIRED_TOKEN(401,"Expired token"),
    INVALID_RECAPTCHA_TOKEN(401,"Invalid recaptcha token"),
    USER_NOT_FOUND(404,"No User found"),
    USER_ACCOUNT_LOCKED(423,"Account locked"),
    USER_ACCOUNT_SUSPENDED(403,"Account suspended"),
    ALREADY_EXISTING_USER(409,"Already existing user"),
    NO_PERMISSION(403,"No permission to access"),
    INTERNAL_SERVER_ERROR(500,"Server error occurred"),
    APPLICATION_NOT_FOUND(404,"No application found"),
    URI_NOT_ACCEPTED(418,"Uri is not authorized"),
    SECRET_INVALID(403,"Client secret is invalid")
}