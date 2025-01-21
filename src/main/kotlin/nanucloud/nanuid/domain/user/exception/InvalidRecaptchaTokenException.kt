package nanucloud.nanuid.domain.user.exception

import nanucloud.nanuid.global.error.exception.ErrorCode
import nanucloud.nanuid.global.error.exception.NanuIdException

object InvalidRecaptchaTokenException : NanuIdException(ErrorCode.INVALID_RECAPTCHA_TOKEN)
