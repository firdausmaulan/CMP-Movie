package fd.cmp.movie

import android.app.Application
import fd.cmp.movie.di.initKoin

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

}