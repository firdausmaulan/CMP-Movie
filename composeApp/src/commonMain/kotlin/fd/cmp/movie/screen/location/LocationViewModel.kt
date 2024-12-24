package fd.cmp.movie.screen.location

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import fd.cmp.movie.data.location.LocationService
import fd.cmp.movie.data.model.LocationData
import kotlinx.coroutines.launch

class LocationViewModel(locationService: LocationService) : KLocationViewModel(locationService) {

    var state by mutableStateOf<LocationState>(LocationState.Idle)

    fun searchLocation(query: String) {
        viewModelScope.launch {
            state = try {
                val locations = fetchSuggestions(query)
                if (locations.isEmpty()) {
                    LocationState.Idle
                } else {
                    LocationState.Success(locations)
                }
            } catch (e: Exception) {
                LocationState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun setLocation(location: LocationData) {
        viewModelScope.launch {
            val details = getPlaceDetails(location.placeId.toString())
            if (details == null) {
                LocationState.Idle
                return@launch
            }
            address = details.address.toString()
            locationData = LocationData(
                latitude = details.latitude,
                longitude = details.longitude,
                displayName = address
            )
            val finalLocation = LocationData(
                placeId = location.placeId,
                primaryText = location.primaryText,
                fullText = location.fullText,
                displayName = address,
                latitude = details.latitude,
                longitude = details.longitude,
            )
            selectedLocation = finalLocation
            LocationState.Update
        }
    }
}