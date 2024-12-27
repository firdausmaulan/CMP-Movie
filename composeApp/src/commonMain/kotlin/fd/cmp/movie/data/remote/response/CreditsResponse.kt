package fd.cmp.movie.data.remote.response


import fd.cmp.movie.data.model.Cast
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsResponse(
    @SerialName("id")
    var id: Int? = null,
    @SerialName("cast")
    var cast: List<Cast?>? = null,
)