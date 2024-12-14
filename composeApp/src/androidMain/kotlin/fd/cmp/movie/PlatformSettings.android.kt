package fd.cmp.movie

import android.content.Context

actual fun providePlatformSettings(): PlatformSettings {
    return PlatformSettings(MovieApp.appContext)
}