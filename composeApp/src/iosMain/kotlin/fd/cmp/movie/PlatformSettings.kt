package fd.cmp.movie

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

actual class PlatformSettings {
    actual fun createSettings(): Settings {
        val delegate = NSUserDefaults.standardUserDefaults
        return NSUserDefaultsSettings(delegate)
    }
}