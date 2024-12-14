package fd.cmp.movie

import com.russhwolf.settings.Settings

expect class PlatformSettings {
    fun createSettings(): Settings
}

expect fun providePlatformSettings(): PlatformSettings