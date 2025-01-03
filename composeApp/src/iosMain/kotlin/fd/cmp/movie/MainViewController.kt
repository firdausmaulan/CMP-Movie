package fd.cmp.movie

import androidx.compose.ui.window.ComposeUIViewController
import fd.cmp.movie.app.App
import fd.cmp.movie.data.local.db.Database
import fd.cmp.movie.data.local.db.DriverFactory
import io.github.kgooglemap.IOSKGoogleMap
import io.github.tbib.klocation.IOSKLocationServices

fun MainViewController() = ComposeUIViewController {
    Database.init(driverFactory = DriverFactory())
    IOSKLocationServices().requestPermission()
    IOSKGoogleMap.init("YOUR GOOGLE MAP KEY")
    App()
}