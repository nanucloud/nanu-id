package nanucloud.nanuid.domain.user.exception

import nanucloud.nanuid.global.error.exception.ErrorCode
import nanucloud.nanuid.global.error.exception.NanuIdException

object UserAccountSuspendedException : NanuIdException(ErrorCode.USER_ACCOUNT_SUSPENDED)
