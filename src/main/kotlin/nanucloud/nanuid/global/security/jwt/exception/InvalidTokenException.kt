package nanucloud.nanuid.global.security.jwt.exception

import nanucloud.nanuid.global.error.exception.ErrorCode
import nanucloud.nanuid.global.error.exception.NanuIdException

object InvalidTokenException : NanuIdException(ErrorCode.INVALID_TOKEN)
