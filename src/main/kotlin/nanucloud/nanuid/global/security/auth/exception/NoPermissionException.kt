package nanucloud.nanuid.global.security.auth.exception

import nanucloud.nanuid.global.error.exception.ErrorCode
import nanucloud.nanuid.global.error.exception.NanuIdException

object NoPermissionException : NanuIdException(ErrorCode.NO_PERMISSION)
