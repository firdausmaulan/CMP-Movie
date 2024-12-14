package fd.cmp.movie.data.local

import fd.cmp.movie.PlatformSettings

class AppSettings(platformSettings: PlatformSettings) {
    private val settings = platformSettings.createSettings()

    fun saveToken(token: String?) {
        if (token != null) settings.putString("token", token)
    }

    fun getToken(): String {
        return settings.getString("token", defaultValue = "")
    }

    fun clearToken() {
        settings.remove("token")
    }
}