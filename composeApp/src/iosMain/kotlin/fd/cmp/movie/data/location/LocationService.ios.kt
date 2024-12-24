package fd.cmp.movie.data.location
import platform.CoreLocation.CLGeocoder
import platform.CoreLocation.CLLocation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class LocationService {
    private val geocoder = CLGeocoder()

    actual suspend fun getLocationName(latitude: Double, longitude: Double): LocationDetailResult {
        return suspendCoroutine { continuation ->
            val location = CLLocation(latitude, longitude)

            geocoder.reverseGeocodeLocation(location) { placemarks, error ->
                if (error != null) {
                    continuation.resume(LocationDetailResult(error = error.localizedDescription))
                    return@reverseGeocodeLocation
                }

                if (placemarks.isNullOrEmpty()) {
                    continuation.resume(LocationDetailResult(error = "No address found"))
                    return@reverseGeocodeLocation
                }

                val placemark = placemarks[0]
                continuation.resume(
                    LocationDetailResult(
                        addressLine = listOfNotNull(
                            placemark.subThoroughfare,
                            placemark.thoroughfare,
                            placemark.locality,
                            placemark.administrativeArea,
                            placemark.country
                        ).joinToString(", "),
                        locality = placemark.locality,
                        subLocality = placemark.subLocality,
                        adminArea = placemark.administrativeArea,
                        countryName = placemark.country
                    )
                )
            }
        }
    }
}

actual fun provideLocationService(): LocationService {
    return LocationService()
}