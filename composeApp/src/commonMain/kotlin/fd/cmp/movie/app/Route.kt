package fd.cmp.movie.app
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    object Login : Route

    @Serializable
    object MovieList : Route

    @Serializable
    data class MovieDetail(val objectId: Int?, val genres : String?) : Route

    @Serializable
    object User : Route

    @Serializable
    data class Location(val locationData : String?) : Route

    @Serializable
    object Photo : Route
}