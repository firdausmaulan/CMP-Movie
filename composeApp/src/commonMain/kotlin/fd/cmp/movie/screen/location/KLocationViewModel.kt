package fd.cmp.movie.screen.location

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fd.cmp.movie.data.location.LocationService
import fd.cmp.movie.data.model.LocationData
import fd.cmp.movie.helper.Constants
import io.github.kgooglemap.KPlacesHelper
import io.github.kgooglemap.utils.PlaceDetails
import io.github.tbib.klocation.KLocationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

open class KLocationViewModel(private val locationService: LocationService) : ViewModel() {

    var selectedLocation: LocationData? = null

    var locationData by mutableStateOf<LocationData?>(null)

    var address by mutableStateOf("")

    private val googlePlaces = KPlacesHelper()

    val kLocationService = KLocationService()

    var isGPSEnabled by mutableStateOf(true)
        protected set

    var requestPermission by mutableStateOf(false)

    init {
        viewModelScope.launch {
            kLocationService.gpsStateFlow().collect { gps ->
                isGPSEnabled = gps
            }
        }
    }

    fun enableGPSAndLocation() {
        requestPermission = true
    }

    fun getCurrentLocation() {
        viewModelScope.launch {
            val location = kLocationService.getCurrentLocation()
            address = locationService.getLocationName(location.latitude, location.longitude).addressLine.toString()
            locationData = LocationData(
                latitude = location.latitude,
                longitude = location.longitude,
                displayName = address
            )
        }
    }

    fun initLocation(initialLocation: LocationData) {
        viewModelScope.launch {
            kotlinx.coroutines.delay(1000)
            address = initialLocation.displayName.toString()
            locationData = initialLocation
        }
    }

    protected suspend fun fetchSuggestions(query: String): List<LocationData> {
        return withContext(Dispatchers.IO) {
            try {
                // Use suspendCancellableCoroutine to bridge the callback-based API with Kotlin coroutines
                suspendCancellableCoroutine { continuation ->
                    googlePlaces.fetchSuggestions(query) { suggestions ->
                        // Check if suggestions are not null and resume the coroutine with the result
                        val locations = suggestions.map {
                            LocationData(
                                placeId = it.placeId,
                                primaryText = it.primaryText,
                                fullText = it.fullText,
                                displayName = it.fullText
                            )
                        }
                        continuation.resume(locations)
                    }
                }
            } catch (e: Exception) {
                // Handle any exceptions that occur during the fetching
                e.printStackTrace()
                emptyList() // Return an empty list on error
            }
        }
    }

    suspend fun getPlaceDetails(placeId: String): PlaceDetails? {
        return withContext(Dispatchers.IO) {
            try {
                suspendCancellableCoroutine { continuation ->
                    googlePlaces.fetchPlaceDetails(placeId) {
                        continuation.resume(it)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}