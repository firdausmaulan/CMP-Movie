package fd.cmp.movie
import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual class PlatformSettings(private val context: Context) {
    actual fun createSettings(): Settings {
        val delegate = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(delegate)
    }
}