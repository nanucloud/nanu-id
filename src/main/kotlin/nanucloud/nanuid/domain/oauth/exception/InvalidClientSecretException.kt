package nanucloud.nanuid.domain.oauth.exception

import nanucloud.nanuid.global.error.exception.ErrorCode
import nanucloud.nanuid.global.error.exception.NanuIdException

object InvalidClientSecretException : NanuIdException(ErrorCode.SECRET_INVALID)
