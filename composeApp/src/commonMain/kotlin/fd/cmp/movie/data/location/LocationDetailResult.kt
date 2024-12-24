package fd.cmp.movie.data.location

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDetailResult(
    @SerialName("address_line")
    val addressLine: String? = null,
    @SerialName("locality")
    val locality: String? = null,
    @SerialName("sub_locality")
    val subLocality: String? = null,
    @SerialName("admin_area")
    val adminArea: String? = null,
    @SerialName("country_name")
    val countryName: String? = null,
    @SerialName("error")
    val error: String? = null
)