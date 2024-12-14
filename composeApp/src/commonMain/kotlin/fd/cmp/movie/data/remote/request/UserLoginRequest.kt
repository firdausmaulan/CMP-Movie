package fd.cmp.movie.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLoginRequest(
    @SerialName("email")
    var email: String? = null,
    @SerialName("password")
    var password: String? = null
)
