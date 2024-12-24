package fd.cmp.movie.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Build
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class LocationService private constructor(private val context: Context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var instance: LocationService

        fun initialize(context: Context) {
            instance = LocationService(context.applicationContext)
        }

        fun getInstance(): LocationService {
            if (!::instance.isInitialized) {
                throw IllegalStateException("LocationService must be initialized first")
            }
            return instance
        }
    }

    actual suspend fun getLocationName(latitude: Double, longitude: Double): LocationDetailResult {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())

            // For Android API 33 and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                return suspendCoroutine { continuation ->
                    geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                        if (addresses.isNotEmpty()) {
                            val address = addresses[0]
                            continuation.resume(
                                LocationDetailResult(
                                    addressLine = address.getAddressLine(0),
                                    locality = address.locality,
                                    subLocality = address.subLocality,
                                    adminArea = address.adminArea,
                                    countryName = address.countryName
                                )
                            )
                        } else {
                            continuation.resume(
                                LocationDetailResult(error = "No address found")
                            )
                        }
                    }
                }
            } else {
                // For older Android versions
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    return LocationDetailResult(
                        addressLine = address.getAddressLine(0),
                        locality = address.locality,
                        subLocality = address.subLocality,
                        adminArea = address.adminArea,
                        countryName = address.countryName
                    )
                }
            }
            LocationDetailResult(
                error = "No address found"
            )
        } catch (e: Exception) {
            LocationDetailResult(
                error = e.message ?: "Error getting location name"
            )
        }
    }
}

actual fun provideLocationService(): LocationService {
    return LocationService.getInstance()
}