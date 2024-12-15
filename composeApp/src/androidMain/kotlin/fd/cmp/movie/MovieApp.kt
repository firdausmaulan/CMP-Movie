package fd.cmp.movie

import android.app.Application
import android.content.Context
import fd.cmp.movie.data.local.db.Database
import fd.cmp.movie.data.local.db.DriverFactory
import fd.cmp.movie.di.initKoin

class MovieApp : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        Database.init(DriverFactory(this))
        initKoin()
    }

}