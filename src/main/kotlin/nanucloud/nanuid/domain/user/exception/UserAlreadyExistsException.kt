package nanucloud.nanuid.domain.user.exception

import nanucloud.nanuid.global.error.exception.ErrorCode
import nanucloud.nanuid.global.error.exception.NanuIdException

object UserAlreadyExistsException : NanuIdException(ErrorCode.ALREADY_EXISTING_USER)
