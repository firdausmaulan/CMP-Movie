package fd.cmp.movie.data.model

import fd.cmp.movie.helper.Constants
import fd.cmp.movie.helper.TextHelper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("backdrop_path_url")
    val backdropPathUrl: String? = Constants.IMAGE_URL + backdropPath,
    @SerialName("genres")
    val genres: List<Genre?>? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int?>? = null,
    @SerialName("genre_names")
    val genreNames: List<String?>? = null,
    @SerialName("formatted_genre_names")
    val formattedGenreNames: String? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("poster_path_url")
    val posterPathUrl: String? = Constants.IMAGE_URL + posterPath,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("formatted_vote_average")
    val formattedVoteAverage: String? = "${TextHelper.roundToOneDecimal(voteAverage ?: 0.0)}/10",
    @SerialName("cast")
    val cast: List<Cast?>? = null,
)