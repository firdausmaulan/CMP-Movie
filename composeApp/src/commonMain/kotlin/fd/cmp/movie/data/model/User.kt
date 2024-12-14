package fd.cmp.movie.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null
)