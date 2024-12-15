package fd.cmp.movie.data.local.keyval

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import fd.cmp.movie.MovieApp

actual class PlatformSettings(private val context: Context) {
    actual fun createSettings(): Settings {
        val delegate = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(delegate)
    }
}

actual fun providePlatformSettings(): PlatformSettings {
    return PlatformSettings(MovieApp.appContext)
}