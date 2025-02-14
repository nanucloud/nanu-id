package nanucloud.nanuid.domain.auth.exception

import nanucloud.nanuid.global.error.exception.ErrorCode
import nanucloud.nanuid.global.error.exception.NanuIdException

object NotFoundException : NanuIdException(ErrorCode.NOT_FOUND)
