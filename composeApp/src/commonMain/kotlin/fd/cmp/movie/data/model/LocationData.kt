package fd.cmp.movie.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class LocationData(
    @SerialName("place_id")
    val placeId: String? = null,
    @SerialName("primary_text")
    val primaryText: String? = null,
    @SerialName("full_text")
    val fullText: String? = null,
    @SerialName("display_name")
    val displayName: String? = null,
    @SerialName("lat")
    val latitude: Double? = null,
    @SerialName("lon")
    val longitude: Double? = null
) {
    companion object {
        fun fromJson(json: String): LocationData {
            return Json.decodeFromString(serializer(), json)
        }
        fun toJson(locationData: LocationData): String {
            return Json.encodeToString(serializer(), locationData)
        }
    }
}