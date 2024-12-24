package fd.cmp.movie

import android.app.Application
import fd.cmp.movie.data.local.db.Database
import fd.cmp.movie.data.local.db.DriverFactory
import fd.cmp.movie.data.local.keyval.PlatformSettings
import fd.cmp.movie.data.location.LocationService
import fd.cmp.movie.di.initKoin

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        PlatformSettings.initialize(this)
        Database.init(DriverFactory(this))
        LocationService.initialize(this)
        initKoin()
    }

}