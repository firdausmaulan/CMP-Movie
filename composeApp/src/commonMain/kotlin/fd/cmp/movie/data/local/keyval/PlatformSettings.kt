package fd.cmp.movie.data.local.keyval

import com.russhwolf.settings.Settings

expect class PlatformSettings {
    fun createSettings(): Settings
}

expect fun providePlatformSettings(): PlatformSettings