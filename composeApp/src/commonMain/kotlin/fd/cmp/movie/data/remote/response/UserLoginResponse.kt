package fd.cmp.movie.data.remote.response

import fd.cmp.movie.data.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLoginResponse(
    @SerialName("status")
    val status: Int? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("user")
    val user: User? = null
)