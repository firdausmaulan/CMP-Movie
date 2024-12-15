package fd.cmp.movie

import androidx.compose.ui.window.ComposeUIViewController
import fd.cmp.movie.app.App
import fd.cmp.movie.data.local.db.Database
import fd.cmp.movie.data.local.db.DriverFactory

fun MainViewController() = ComposeUIViewController {
    Database.init(driverFactory = DriverFactory())
    App()
}