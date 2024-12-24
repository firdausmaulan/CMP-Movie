package fd.cmp.movie.data.local.keyval

import android.annotation.SuppressLint
import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual class PlatformSettings private constructor(private val context: Context) {
    actual fun createSettings(): Settings {
        val delegate = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(delegate)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var instance: PlatformSettings

        fun initialize(context: Context) {
            instance = PlatformSettings(context.applicationContext)
        }

        fun getInstance(): PlatformSettings {
            if (!::instance.isInitialized) {
                throw IllegalStateException("PlatformSettings must be initialized first")
            }
            return instance
        }
    }
}

// Usage in shared code
actual fun providePlatformSettings(): PlatformSettings {
    return PlatformSettings.getInstance()
}