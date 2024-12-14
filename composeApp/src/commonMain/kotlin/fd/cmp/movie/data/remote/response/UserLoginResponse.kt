package fd.cmp.movie.data.remote.response

import fd.cmp.movie.data.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("genres")
    val genres: List<Genre>? = null
)