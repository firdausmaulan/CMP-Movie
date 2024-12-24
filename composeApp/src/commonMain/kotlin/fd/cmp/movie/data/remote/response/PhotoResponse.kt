package fd.cmp.movie.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoResponse(
    @SerialName("status")
    val status: Int? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("imagePath")
    val imagePath: String? = null,
)