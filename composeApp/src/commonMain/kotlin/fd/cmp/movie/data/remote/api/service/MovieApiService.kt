package fd.cmp.movie.data.remote.api.service

import fd.cmp.movie.data.model.Movie
import fd.cmp.movie.data.remote.api.core.ApiResponse
import fd.cmp.movie.data.remote.api.core.AppHttpClient
import fd.cmp.movie.data.remote.request.MovieRequest
import fd.cmp.movie.data.remote.response.GenresResponse
import fd.cmp.movie.data.remote.response.MoviesResponse

class MovieApiService(private val appHttpClient: AppHttpClient) {

    suspend fun search(request: MovieRequest): ApiResponse<MoviesResponse> {
        val parameters = mapOf(
            "query" to request.query,
            "page" to request.page.toString(),
            "language" to request.language
        )
        return appHttpClient.get(endpoint = "search/movie", parameters = parameters)
    }

    suspend fun genres() : ApiResponse<GenresResponse> {
        return appHttpClient.get(endpoint = "genre/movie/list")
    }

    suspend fun detail(id: Int?): ApiResponse<Movie> {
        return appHttpClient.get(endpoint = "movie/$id")
    }

}