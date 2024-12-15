package fd.cmp.movie.data.local.keyval

class AppSettings(platformSettings: PlatformSettings) {
    private val settings = platformSettings.createSettings()

    fun saveToken(token: String?) {
        if (token != null) settings.putString("token", token)
    }

    fun getToken(): String {
        return settings.getString("token", defaultValue = "")
    }

    fun saveEmail(email: String?) {
        if (email != null) settings.putString("email", email)
    }

    fun getEmail(): String {
        return settings.getString("email", defaultValue = "")
    }

    fun clear() {
        settings.remove("token")
        settings.remove("email")
    }
}