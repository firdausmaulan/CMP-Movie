package fd.cmp.movie.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: Int?,
    @SerialName("email")
    val email: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("phone")
    val phone: String?,
    @SerialName("dateOfBirth")
    val dateOfBirth: String?,
    @SerialName("imagePath")
    val imagePath: String?,
    @SerialName("token")
    val token: String? = null
)