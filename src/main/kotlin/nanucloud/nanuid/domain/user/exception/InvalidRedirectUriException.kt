package nanucloud.nanuid.domain.user.exception

import nanucloud.nanuid.global.error.exception.ErrorCode
import nanucloud.nanuid.global.error.exception.NanuIdException

object InvalidRedirectUriException : NanuIdException(ErrorCode.URI_NOT_ACCEPTED)
