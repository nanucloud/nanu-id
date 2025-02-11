package nanucloud.nanuid.domain.oauth.exception

import nanucloud.nanuid.global.error.exception.ErrorCode
import nanucloud.nanuid.global.error.exception.NanuIdException

object InvalidCodeException : NanuIdException(ErrorCode.INVALID_TOKEN)
