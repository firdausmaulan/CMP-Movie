package fd.cmp.movie.data.local.keyval

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings


actual class PlatformSettings {
    actual fun createSettings(): Settings {
        val delegate = NSUserDefaults.standardUserDefaults
        return NSUserDefaultsSettings(delegate)
    }
}

actual fun providePlatformSettings(): PlatformSettings {
    return PlatformSettings()
}