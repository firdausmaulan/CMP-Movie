package fd.cmp.movie.data.model

import fd.cmp.movie.helper.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: Long?,
    @SerialName("email")
    val email: String?,
    @SerialName("name")
    var name: String?,
    @SerialName("phone")
    var phone: String?,
    @SerialName("dateOfBirth")
    var dateOfBirth: String?,
    @SerialName("address")
    var address: String?,
    @SerialName("latitude")
    var latitude: Double?,
    @SerialName("longitude")
    var longitude: Double?,
    @SerialName("imagePath")
    var imagePath: String?,
    @SerialName("imageUrl")
    var imageUrl: String? = Constants.BASE_USER_URL + imagePath,
    @SerialName("token")
    val token: String? = null
)