package nanucloud.nanuid.domain.user.exception

import nanucloud.nanuid.global.error.exception.ErrorCode
import nanucloud.nanuid.global.error.exception.NanuIdException

object UserAccountLockedException : NanuIdException(ErrorCode.USER_ACCOUNT_LOCKED)
