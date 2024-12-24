package fd.cmp.movie.screen.location

import fd.cmp.movie.data.model.LocationData

sealed class LocationState {
    object Idle : LocationState()
    object Update : LocationState()
    data class Success(val results: List<LocationData>) : LocationState()
    data class Error(val message: String) : LocationState()
}