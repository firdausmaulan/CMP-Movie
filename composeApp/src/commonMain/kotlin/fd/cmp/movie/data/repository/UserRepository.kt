package fd.cmp.movie.data.repository

import fd.cmp.movie.data.model.Movie
import fd.cmp.movie.data.remote.api.core.ApiResponse
import fd.cmp.movie.data.remote.request.MovieRequest
import fd.cmp.movie.data.remote.response.MoviesResponse

interface UserRepository {
    suspend fun search(request: MovieRequest): ApiResponse<MoviesResponse>
    suspend fun detail(id: Int?): ApiResponse<Movie>
}