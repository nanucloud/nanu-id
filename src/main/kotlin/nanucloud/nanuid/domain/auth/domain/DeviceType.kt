package nanucloud.nanuid.domain.auth.domain

enum class DeviceType(val type: String) {
    WEB_UNKNOWN("WEB_UNKNOWN"),
    ANDROID("ANDROID"),
    IOS("IOS"),
    WINDOWS("WINDOWS"),
    MAC("MAC")
}