package nanucloud.nanuid.global.error.exception

enum class ErrorCode (
    val httpStatus: Int,
    val message : String
) {
    BAD_REQUEST(400,"Bad request"),
    INVALID_TOKEN(401,"Invalid token"),
    EXPIRED_TOKEN(401,"expired token"),
    USER_NOT_FOUND(404,"No User found"),
    NO_PERMISSION(403,"No permission to access"),
    INTERNAL_SERVER_ERROR(500,"Server error occurred")
}