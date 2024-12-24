package fd.cmp.movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fd.cmp.movie.app.App
import io.github.kgooglemap.AndroidKGoogleMap
import io.github.tbib.klocation.AccuracyPriority
import io.github.tbib.klocation.AndroidKLocationService
import io.github.tbib.klocation.KLocationService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidKLocationService.initialization(this, AccuracyPriority.BALANCED_POWER_ACCURACY)
        AndroidKGoogleMap.initialization(this, "your-key")
        setContent {
            KLocationService().ListenerToPermission()
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}