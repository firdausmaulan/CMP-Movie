package fd.cmp.movie.screen.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fd.cmp.movie.helper.Constants
import io.github.kgooglemap.KMapController
import io.github.kgooglemap.ui.KGoogleMapView
import io.github.kgooglemap.utils.LatLng
import io.github.kgooglemap.utils.Markers

@Composable
fun MapScreen(
    latitude: Double? = Constants.DEFAULT_LATITUDE,
    longitude: Double? = Constants.DEFAULT_LONGITUDE,
    zoom: Float? = 13f,
    mapModifier: Modifier = Modifier
        .fillMaxWidth()
        .height(250.dp),
) {
    val mapController = remember {
        KMapController(
            initPosition = LatLng(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE),
            zoom = zoom ?: 13f
        )
    }

    // Handle location updates
    LaunchedEffect(latitude, longitude) {
        if (latitude != null && longitude != null) {
            val newPosition = LatLng(latitude, longitude)

            // Clear existing markers (if needed)
            mapController.clearMarkers()

            // Move to new location
            mapController.goToLocation(newPosition)

            // Add new marker
            mapController.addMarkers(
                listOf(
                    Markers(
                        newPosition,
                        "My Location",
                        ""
                    )
                )
            )
        }
    }

    Box(modifier = mapModifier) {
        KGoogleMapView(controller = mapController)
    }
}